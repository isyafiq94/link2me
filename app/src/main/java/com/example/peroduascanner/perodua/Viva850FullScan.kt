package com.example.peroduascanner.perodua

import com.example.peroduascanner.model.ScanResult

/**
 * Full scan procedure for Viva 850 vehicles.
 */
class Viva850FullScan(private val ecu: PeroduaECU) {
    suspend fun run(): ScanResult {
        // In a real implementation you'd sequence supported commands, retry and normalize results.
        return ecu.fullScan()
    }
}
