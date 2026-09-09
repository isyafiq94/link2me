package com.example.peroduascanner.obd

/**
 * Representation of a single OBD command.
 */
interface OBDCommand {
    val command: String
    fun encode(prefixWithCR: Boolean = true): ByteArray {
        val cmd = if (prefixWithCR) "$command\r" else command
        return cmd.toByteArray()
    }
}

/**
 * Simple concrete implementation for arbitrary OBD command strings.
 */
class SimpleObdCommand(override val command: String) : OBDCommand
