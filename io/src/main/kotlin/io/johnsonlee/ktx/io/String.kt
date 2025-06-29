package io.johnsonlee.ktx.io

import java.io.InputStream

fun String.inputStream(): InputStream = StringInputStream(this)
