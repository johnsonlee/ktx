package io.johnsonlee.ktx.security

import io.johnsonlee.ktx.io.inputStream
import java.io.File
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@Throws(NoSuchAlgorithmException::class)
fun InputStream.hash(algorithm: String): String {
    val md = MessageDigest.getInstance(algorithm)
    val buffer = ByteArray(8192)
    var readBytes: Int

    while (read(buffer).also { readBytes = it } != -1) {
        md.update(buffer, 0, readBytes)
    }

    return md.digest().joinToString("") { "%02x".format(it) }
}

@Throws(NoSuchAlgorithmException::class)
fun File.hash(algorithm: String): String = inputStream().use {
    it.hash(algorithm)
}

@Throws(NoSuchAlgorithmException::class)
fun String.hash(algorithm: String): String = inputStream().use {
    it.hash(algorithm)
}

fun File.md5(): String = hash("MD5")

fun File.sha1(): String = hash("SHA-1")