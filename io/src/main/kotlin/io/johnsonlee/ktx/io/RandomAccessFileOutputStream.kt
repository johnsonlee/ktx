package io.johnsonlee.ktx.io

import java.io.OutputStream
import java.io.RandomAccessFile

class RandomAccessFileOutputStream(private val raf: RandomAccessFile) : OutputStream() {
    override fun write(b: Int) = raf.write(b)
    override fun write(b: ByteArray, off: Int, len: Int) = raf.write(b, off, len)
    override fun flush() = raf.channel.force(false)
    override fun close() = raf.close()
    fun position(): Long = raf.filePointer
    fun seek(position: Long) = raf.seek(position)
}