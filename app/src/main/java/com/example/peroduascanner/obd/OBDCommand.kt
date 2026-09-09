package com.example.peroduascanner.obd

/**
 * Representation of a single OBD command.
 */
interface OBDCommand {
    val command: String
    fun encode(): ByteArray = command.toByteArray()
}
