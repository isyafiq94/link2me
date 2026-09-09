package com.example.peroduascanner.perodua

import com.example.peroduascanner.model.ScanResult

/**
 * Abstraction for Perodua ECU interactions.
 */
class PeroduaECU(private val protocol: Any) {
    fun readVin(): String? {
        // TODO: implement VIN read
        return null
    }

    fun fullScan(): ScanResult {
        // TODO: perform a full scan and return a ScanResult
        return ScanResult(success = false, details = "Not implemented")
    }
}
