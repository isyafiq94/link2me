package com.example.peroduascanner.model

/**
 * Result object returned from scans.
 */
data class ScanResult(
    val success: Boolean,
    val details: String? = null,
    val dtcs: List<String> = emptyList()
)
