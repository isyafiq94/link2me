package com.example.peroduascanner

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.peroduascanner.bluetooth.BluetoothManager
import com.example.peroduascanner.obd.OBDCommand
import com.example.peroduascanner.obd.OBDScanner
import com.example.peroduascanner.obd.SimpleObdCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private val btManager: BluetoothManager by lazy { BluetoothManager(this) }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val granted = perms.values.all { it }
        if (!granted) {
            Toast.makeText(this, "Required permissions not granted", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // NOTE: No UI layout by default. This activity runs headless for quick scanning flows.
        requestRuntimePermissionsIfNeeded()

        // Example flow: pick first paired device, connect and run a small OBD scan
        scope.launch {
            try {
                val device: BluetoothDevice? = btManager.getPairedObdDevice()
                if (device == null) {
                    Toast.makeText(this@MainActivity, "No paired OBD device found", Toast.LENGTH_LONG).show()
                    return@launch
                }

                val transport = btManager.connectToObd(device)
                val scanner = OBDScanner(transport)

                // Example commands: request engine RPM (01 0C) and vehicle speed (01 0D)
                val commands: List<OBDCommand> = listOf(
                    SimpleObdCommand("01 0C"), // Engine RPM
                    SimpleObdCommand("01 0D"), // Vehicle speed
                    SimpleObdCommand("09 02")  // VIN (may require different handling)
                )

                val responses = scanner.scan(commands)
                for (r in responses) {
                    Log.i(TAG, "OBD response: $r")
                }

                Toast.makeText(this@MainActivity, "Scan complete (check log)", Toast.LENGTH_LONG).show()

                transport.close()

            } catch (e: Exception) {
                Log.e(TAG, "Scan failed", e)
                Toast.makeText(this@MainActivity, "Scan failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestRuntimePermissionsIfNeeded() {
        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions += Manifest.permission.BLUETOOTH_SCAN
            permissions += Manifest.permission.BLUETOOTH_CONNECT
        } else {
            // older platforms
            permissions += Manifest.permission.BLUETOOTH
            permissions += Manifest.permission.BLUETOOTH_ADMIN
            // location may be required for discovery on some devices
            permissions += Manifest.permission.ACCESS_FINE_LOCATION
        }

        requestPermissionsLauncher.launch(permissions.toTypedArray())
    }
}
