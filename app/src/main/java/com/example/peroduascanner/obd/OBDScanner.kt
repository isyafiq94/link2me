package com.example.peroduascanner.obd

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException

/**
 * High-level scanner that sends OBD commands and collects responses.
 * This implementation expects the transport to behave synchronously (blocking IO).
 */
class OBDScanner(private val transport: OBDTransport) {
    /**
     * Send each command and return the raw response string for each.
     * Uses a default per-command timeout to avoid blocking forever.
     */
    suspend fun scan(commands: List<OBDCommand>, perCommandTimeoutMs: Long = 5000L): List<String> {
        return withContext(Dispatchers.IO) {
            val results = mutableListOf<String>()
            for (cmd in commands) {
                try {
                    withTimeout(perCommandTimeoutMs) {
                        transport.write(cmd.encode())
                        val resp = transport.readUntilPrompt()
                        results += resp
                    }
                } catch (e: Exception) {
                    results += "ERROR: ${e.message}"
                }
            }
            results
        }
    }
}

/**
 * Minimal transport abstraction used by the scanner.
 */
interface OBDTransport {
    fun write(data: ByteArray)
    /**
     * Read one response as a String. Implementations should block until a response is available
     * or throw on error. readUntilPrompt is a convenience implemented by default.
     */
    fun read(): String
    /**
     * Default helper: read until '>' prompt which many ELM-style adapters use to signal end.
     */
    fun readUntilPrompt(): String = read()
    fun close()
}
