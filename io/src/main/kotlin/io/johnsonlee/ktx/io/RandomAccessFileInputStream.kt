package io.johnsonlee.ktx.io

import java.io.RandomAccessFile

class RandomAccessFileInputStream(private val raf: RandomAccessFile) : java.io.InputStream() {
    override fun read(): Int = raf.read()
    override fun read(b: ByteArray, off: Int, len: Int): Int = raf.read(b, off, len)
    override fun close() = raf.close()
    override fun skip(n: Long): Long = raf.skipBytes(n.toInt()).toLong()
    override fun available(): Int = raf.length().toInt() - raf.filePointer.toInt()
    override fun markSupported(): Boolean = false
    fun seek(position: Long) = raf.seek(position)
    fun position(): Long = raf.filePointer
}