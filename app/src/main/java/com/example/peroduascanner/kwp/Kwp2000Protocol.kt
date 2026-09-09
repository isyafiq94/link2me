package com.example.peroduascanner.kwp

/**
 * Lightweight KWP2000 protocol helper. This is a basic framing example and will
 * need adjustments for real-world ECU interactions (timings, checksums, physical layer).
 */
class Kwp2000Protocol {
    fun buildRequest(serviceId: Int, payload: ByteArray = ByteArray(0)): ByteArray {
        // Very small example frame: [sid][len][payload...]
        val frame = ByteArray(2 + payload.size)
        frame[0] = serviceId.toByte()
        frame[1] = payload.size.toByte()
        System.arraycopy(payload, 0, frame, 2, payload.size)
        return frame
    }

    fun parseResponse(raw: ByteArray): KwpResponse {
        // Very simplistic: treat first byte as success flag
        if (raw.isEmpty()) return KwpResponse(false, raw, "empty")
        val success = raw[0].toInt() != 0
        return KwpResponse(success, raw, null)
    }
}
