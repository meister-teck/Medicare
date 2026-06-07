package com.medicare.app.data.api

import com.medicare.app.util.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val prefs: PreferencesManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val token = runBlocking { prefs.tokenFlow.first() }
        val newReq = if (!token.isNullOrBlank() && !req.url.encodedPath.startsWith("/api/auth")) {
            req.newBuilder().addHeader("Authorization", "Bearer $token").build()
        } else req
        return chain.proceed(newReq)
    }
}
