package com.example.peroduascanner.obd

/**
 * Parser for raw OBD responses into structured data. This parser supports a small
 * set of common PIDs and a simple DTC parser for "03" responses.
 */
object OBDParser {
    /**
     * Parse a raw response string and return a map of interpreted values.
     * Example raw: "41 0C 1A F8"
     */
    fun parse(response: String): Map<String, Any> {
        val tokens = response
            .replace("\r", " ")
            .replace("\n", " ")
            .split(Regex("[ ,]+"))
            .filter { it.isNotBlank() }

        if (tokens.isEmpty()) return mapOf("raw" to response)

        // Remove any leading adapter echoes like "ELM327>"
        val cleaned = tokens.filter { it.isNotEmpty() }
        if (cleaned.size >= 2 && cleaned[0].length == 2) {
            // Typical response: [mode+0x40] [pid] [A] [B] ...
            val modeHex = cleaned[0]
            val pidHex = cleaned.getOrNull(1)
            try {
                val mode = Integer.parseInt(modeHex, 16)
                val pid = pidHex?.let { Integer.parseInt(it, 16) }

                if (mode == 0x41 && pid != null) {
                    // PID response for mode 1
                    val dataBytes = cleaned.drop(2).mapNotNull { byteStr ->
                        try { Integer.parseInt(byteStr, 16) } catch (e: Exception) { null }
                    }
                    when (pid) {
                        0x0C -> { // engine RPM: ((A*256)+B)/4
                            if (dataBytes.size >= 2) {
                                val rpm = ((dataBytes[0] shl 8) + dataBytes[1]) / 4
                                return mapOf("pid" to pid, "name" to "engine_rpm", "value" to rpm)
                            }
                        }
                        0x0D -> { // vehicle speed: A
                            if (dataBytes.isNotEmpty()) return mapOf("pid" to pid, "name" to "speed_kmh", "value" to dataBytes[0])
                        }
                        0x05 -> { // coolant temp: A-40
                            if (dataBytes.isNotEmpty()) return mapOf("pid" to pid, "name" to "coolant_c", "value" to dataBytes[0] - 40)
                        }
                        0x0F -> { // intake air temp
                            if (dataBytes.isNotEmpty()) return mapOf("pid" to pid, "name" to "iat_c", "value" to dataBytes[0] - 40)
                        }
                        else -> {
                            return mapOf("pid" to pid, "raw_data" to dataBytes)
                        }
                    }
                }

                // DTC response (mode 3) often begins with 43
                if (modeHex.equals("43", true)) {
                    // Parse DTCs from remaining bytes
                    val dtcBytes = cleaned.drop(1).mapNotNull {
                        try { Integer.parseInt(it, 16) } catch (e: Exception) { null }
                    }
                    val dtcs = parseDtcsFromBytes(dtcBytes)
                    return mapOf("dtcs" to dtcs)
                }

            } catch (e: Exception) {
                // fallback
            }
        }

        return mapOf("raw" to response)
    }

    private fun parseDtcsFromBytes(bytes: List<Int>): List<String> {
        val dtcs = mutableListOf<String>()
        var i = 0
        while (i + 1 < bytes.size) {
            val msb = bytes[i]
            val lsb = bytes[i + 1]
            val dtc = decodeDtc(msb, lsb)
            if (dtc != null) dtcs.add(dtc)
            i += 2
        }
        return dtcs
    }

    private fun decodeDtc(msb: Int, lsb: Int): String? {
        // According to OBD-II: first two bits of msb determine the first char
        val ch = when ((msb and 0xC0) shr 6) {
            0 -> 'P'
            1 -> 'C'
            2 -> 'B'
            3 -> 'U'
            else -> 'P'
        }
        val digit1 = (msb and 0x30) shr 4
        val digit2 = (msb and 0x0F)
        val digit3 = (lsb and 0xF0) shr 4
        val digit4 = (lsb and 0x0F)
        // If all zeros, no more DTCs
        if (msb == 0 && lsb == 0) return null
        return String.format("%c%d%d%d%d", ch, digit1, digit2, digit3, digit4)
    }
}
