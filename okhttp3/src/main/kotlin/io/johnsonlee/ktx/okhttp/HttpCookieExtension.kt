package io.johnsonlee.ktx.okhttp

import java.net.CookieStore
import java.net.HttpCookie
import java.net.URI

fun Collection<HttpCookie>.toCookieStore(): CookieStore {
    return InMemoryCookieStore().apply {
        forEach { cookie ->
            add(URI.create(cookie.domain.removePrefix(".")), cookie)
        }
    }
}