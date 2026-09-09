package com.link2me.obd

import com.link2me.bluetooth.BluetoothManager
import com.link2me.data.model.OBDCommand
import com.link2me.data.model.OBDCommands
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

/**
 * OBD-II Scanner that reads vehicle diagnostics
 */
class OBDScanner(private val bluetoothManager: BluetoothManager) {
    private val parser = OBDParser()

    private val _scanProgress = MutableStateFlow<ScanProgress>(ScanProgress.Idle)
    val scanProgress: StateFlow<ScanProgress> = _scanProgress

    private val _scanResults = MutableStateFlow<Map<String, Any?>>(emptyMap())
    val scanResults: StateFlow<Map<String, Any?>> = _scanResults

    suspend fun initializeAdapter(): Boolean {
        return try {
            Timber.d("Initializing OBD-II adapter")
            bluetoothManager.sendCommand("AT Z")  // Reset
            bluetoothManager.sendCommand("AT E0") // Echo off
            bluetoothManager.sendCommand("AT L0") // Line feeds off
            bluetoothManager.sendCommand("AT S0") // Spaces off
            bluetoothManager.sendCommand("AT SP 0") // Protocol auto-select
            
            Timber.d("Adapter initialized successfully")
            true
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize adapter")
            false
        }
    }

    suspend fun startScan() {
        try {
            _scanProgress.value = ScanProgress.Scanning(0)
            val results = mutableMapOf<String, Any?>()
            val commands = OBDCommands.SUPPORTED_COMMANDS

            for ((index, command) in commands.withIndex()) {
                _scanProgress.value = ScanProgress.Scanning((index + 1) * 100 / commands.size)
                
                Timber.d("Scanning: ${command.name}")
                val response = bluetoothManager.sendCommand(command.command)
                
                if (response != null) {
                    val value = parseCommandResponse(command, response)
                    results[command.name] = value
                    Timber.d("${command.name}: $value ${command.unit}")
                }
            }

            _scanResults.value = results
            _scanProgress.value = ScanProgress.Complete
            Timber.d("Scan completed")
        } catch (e: Exception) {
            Timber.e(e, "Scan failed")
            _scanProgress.value = ScanProgress.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun readErrorCodes(): List<String> {
        return try {
            Timber.d("Reading error codes")
            val response = bluetoothManager.sendCommand("03") ?: return emptyList()
            val codes = parser.parseErrorCodes(response)
            Timber.d("Found ${codes.size} error codes: $codes")
            codes
        } catch (e: Exception) {
            Timber.e(e, "Failed to read error codes")
            emptyList()
        }
    }

    suspend fun clearErrorCodes(): Boolean {
        return try {
            Timber.d("Clearing error codes")
            val response = bluetoothManager.sendCommand("04")
            response != null
        } catch (e: Exception) {
            Timber.e(e, "Failed to clear error codes")
            false
        }
    }

    private fun parseCommandResponse(command: OBDCommand, response: String): Float? {
        return when (command.command) {
            "01 0C" -> parser.parseRPM(response)
            "01 05" -> parser.parseCoolantTemp(response)
            "01 11" -> parser.parseThrottlePosition(response)
            "01 0D" -> parser.parseVehicleSpeed(response)
            "01 0A" -> parser.parseFuelPressure(response)
            "01 14" -> parser.parseOxygenSensor(response)
            "01 0F" -> parser.parseIntakeAirTemp(response)
            "01 2F" -> parser.parseFuelLevel(response)
            else -> null
        }
    }
}

sealed class ScanProgress {
    object Idle : ScanProgress()
    data class Scanning(val progress: Int) : ScanProgress() // 0-100
    object Complete : ScanProgress()
    data class Error(val message: String) : ScanProgress()
}
