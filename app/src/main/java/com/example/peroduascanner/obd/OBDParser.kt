package com.example.peroduascanner.obd

/**
 * Parser for raw OBD responses into structured data.
 */
object OBDParser {
    fun parse(response: String): Map<String, Any> {
        // TODO: implement parsing for standard OBD-II responses
        return mapOf("raw" to response)
    }
}
