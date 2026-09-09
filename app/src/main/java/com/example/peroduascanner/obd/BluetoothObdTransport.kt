package com.example.peroduascanner.obd

import android.bluetooth.BluetoothSocket
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.nio.charset.Charset

/**
 * Bluetooth-backed OBD transport that wraps a connected BluetoothSocket.
 * This class performs simple read/write with basic prompt handling.
 */
class BluetoothObdTransport(private val socket: BluetoothSocket) : OBDTransport {
    private val input = BufferedInputStream(socket.inputStream)
    private val reader = BufferedReader(InputStreamReader(input, Charset.forName("UTF-8")))
    private val out: OutputStream = socket.outputStream

    override fun write(data: ByteArray) {
        out.write(data)
        out.flush()
    }

    override fun read(): String {
        // Read available bytes until '>' prompt or newline sequence.
        val sb = StringBuilder()
        try {
            val buf = CharArray(1024)
            // Read loop: try to read lines until prompt char appears
            while (true) {
                val r = reader.read()
                if (r == -1) break
                val c = r.toChar()
                sb.append(c)
                if (c == '>') break
            }
        } catch (e: Exception) {
            // swallow and return what we got
        }
        return sb.toString().trim()
    }

    override fun readUntilPrompt(): String {
        return read()
    }

    override fun close() {
        try {
            socket.close()
        } catch (ignored: Exception) {
        }
    }
}
