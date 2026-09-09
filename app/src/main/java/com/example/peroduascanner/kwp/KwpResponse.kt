package com.example.peroduascanner.kwp

/**
 * Simple data holder for KWP responses.
 */
data class KwpResponse(
    val success: Boolean,
    val data: ByteArray,
    val message: String? = null
)
