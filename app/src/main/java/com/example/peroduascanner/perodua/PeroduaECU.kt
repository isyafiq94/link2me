package com.example.peroduascanner.perodua

import com.example.peroduascanner.model.ScanResult
import com.example.peroduascanner.obd.OBDScanner
import com.example.peroduascanner.obd.SimpleObdCommand

/**
 * Abstraction for Perodua ECU interactions.
 */
class PeroduaECU(private val scanner: OBDScanner) {
    suspend fun readVin(): String? {
        val responses = scanner.scan(listOf(SimpleObdCommand("09 02")), 4000)
        val raw = responses.firstOrNull() ?: return null
        // VIN responses can be spread across multiple frames; this is a naive attempt
        val cleaned = raw.replace("\r", " ").replace("\n", " ")
        return cleaned
    }

    suspend fun fullScan(): ScanResult {
        // Basic scan: RPM and speed and VIN and DTCs
        val cmds = listOf(
            SimpleObdCommand("01 0C"), // RPM
            SimpleObdCommand("01 0D"), // Speed
            SimpleObdCommand("03"),    // Request DTCs
            SimpleObdCommand("09 02")  // VIN
        )
        val responses = scanner.scan(cmds, 6000)
        return ScanResult(success = true, details = responses.joinToString("\n"))
    }
}
