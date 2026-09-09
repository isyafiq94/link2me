package com.example.peroduascanner.kwp

/**
 * Parser for KWP (K-line) responses — small utility wrapper.
 */
object KwpParser {
    fun parse(raw: ByteArray): KwpResponse {
        // In a real implementation you'd decode headers, lengths, checksums and payloads
        return KwpResponse(success = raw.isNotEmpty(), data = raw)
    }
}
