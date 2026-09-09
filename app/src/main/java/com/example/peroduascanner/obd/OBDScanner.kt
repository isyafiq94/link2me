package com.example.peroduascanner.obd

/**
 * High-level scanner that sends OBD commands and collects responses.
 */
class OBDScanner(private val transport: OBDTransport) {
    suspend fun scan(commands: List<OBDCommand>): List<String> {
        val results = mutableListOf<String>()
        for (cmd in commands) {
            transport.write(cmd.encode())
            val resp = transport.read()
            results += resp
        }
        return results
    }
}

/**
 * Minimal transport abstraction used by the scanner (stub).
 */
interface OBDTransport {
    fun write(data: ByteArray)
    fun read(): String
}
