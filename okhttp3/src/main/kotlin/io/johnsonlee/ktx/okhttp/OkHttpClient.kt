package io.johnsonlee.ktx.okhttp

import okhttp3.Dispatcher
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.internal.threadFactory
import java.net.CookieManager
import java.net.CookieStore
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

private val inMemoryCookieStore = InMemoryCookieStore()

fun okhttpClient(
    useDaemonThread: Boolean = false,
    maxRequestPerMinute: Int = 30,
    cookieStore: CookieStore = inMemoryCookieStore,
): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder().apply {
            header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            header("accept-language", "en-US,en")
            header("cache-control", "no-cache")
            header("pragma", "no-cache")
            header("priority", "u=0, i")
            header("sec-ch-ua", "\"Google Chrome\";v=\"${CHROME_VERSION}\", \"Chromium\";v=\"${CHROME_VERSION}\", \"Not/A)Brand\";v=\"24\"")
            header("sec-ch-ua-mobile", "?0")
            header("sec-ch-ua-platform", "\"macOS\"")
            header("sec-fetch-dest", "document")
            header("sec-fetch-mode", "navigate")
            header("sec-fetch-site", "same-origin")
            header("sec-fetch-user", "?1")
            header("upgrade-insecure-requests", "1")
            header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X ${MACOSX_VERSION}) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/${CHROME_VERSION}.0.0.0 Safari/537.36")
        }.build()
        chain.proceed(request)
    }
    .addInterceptor(DomainRateLimitInterceptor(maxRequestPerMinute))
    .cookieJar(JavaNetCookieJar(CookieManager(cookieStore, null)))
    .dispatcher(Dispatcher(ThreadPoolExecutor(0, Int.MAX_VALUE, 60, TimeUnit.SECONDS, SynchronousQueue(), threadFactory("${OkHttpClient::class.java.name.removePrefix("okhttp3.").removeSuffix("Client")} Dispatcher", useDaemonThread))))
    .build()

private const val CHROME_VERSION = "137"
private const val MACOSX_VERSION = "15_5"