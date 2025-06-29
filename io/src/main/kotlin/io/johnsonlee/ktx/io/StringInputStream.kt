package io.johnsonlee.ktx.io

import java.io.IOException
import java.io.InputStream

class StringInputStream(source: String) : InputStream() {
    private val reader = source.reader()
    private val buffer = CharArray(1024)
    private var bufferPos = 0
    private var bufferLimit = 0
    @Volatile
    private var closed = false

    override fun read(): Int {
        if (check()) return -1
        return buffer[bufferPos++].code
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        if (check()) return -1

        val bytesRead = minOf(len, bufferLimit - bufferPos)
        for (i in 0 until bytesRead) {
            b[off + i] = buffer[bufferPos++].code.toByte()
        }
        return bytesRead
    }

    override fun close() {
        closed = true
        reader.close()
    }

    private fun check(): Boolean {
        if (closed) throw IOException("Stream closed")
        if (bufferPos >= bufferLimit) {
            bufferLimit = reader.read(buffer)
            bufferPos = 0
            if (bufferLimit == -1) return true
        }
        return false
    }
}