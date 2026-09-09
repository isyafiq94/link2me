package com.example.peroduascanner.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import com.example.peroduascanner.obd.BluetoothObdTransport
import java.io.IOException
import java.util.*

private const val TAG = "BluetoothManager"

/**
 * Manages basic Bluetooth discovery and connections.
 * This is a lightweight helper — integrate with proper UI in production.
 */
class BluetoothManager(private val context: Context) {
    private val adapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    /**
     * Returns the first paired device whose name looks like an OBD adapter.
     * Heuristic: name contains "OBD" or "OBDII" or "ELM". Returns null if none.
     */
    fun getPairedObdDevice(): BluetoothDevice? {
        val paired = adapter?.bondedDevices ?: emptySet()
        return paired.firstOrNull { device ->
            val name = device.name ?: ""
            name.contains("OBD", ignoreCase = true) ||
                    name.contains("ELM", ignoreCase = true) ||
                    name.contains("OBDII", ignoreCase = true)
        }
    }

    /**
     * Connects to the given BluetoothDevice using SPP and returns a transport wrapper.
     */
    @Throws(IOException::class)
    fun connectToObd(device: BluetoothDevice): BluetoothObdTransport {
        // Standard SPP UUID used by many OBD-II adapters
        val sppUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(sppUuid)
        adapter?.cancelDiscovery()
        socket.connect()
        Log.i(TAG, "Connected to ${device.address}")
        return BluetoothObdTransport(socket)
    }
}
