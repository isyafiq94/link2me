package com.example.peroduascanner.kwp

/**
 * Parser for KWP (K-line) responses.
 */
object KwpParser {
    fun parse(raw: ByteArray): KwpResponse {
        // TODO: implement parsing logic
        return KwpResponse(success = false, data = raw)
    }
}
