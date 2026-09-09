package com.example.peroduascanner.perodua

import com.example.peroduascanner.model.ScanResult

/**
 * Full scan procedure for Viva 850 vehicles.
 */
class Viva850FullScan(private val ecu: PeroduaECU) {
    fun run(): ScanResult {
        // TODO: orchestrate ECU queries for a full scan
        return ecu.fullScan()
    }
}
