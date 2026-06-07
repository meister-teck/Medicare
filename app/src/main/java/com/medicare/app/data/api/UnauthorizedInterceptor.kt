package com.medicare.app.data.api

import com.medicare.app.util.AuthEventBus
import com.medicare.app.util.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UnauthorizedInterceptor @Inject constructor(
    private val prefs: PreferencesManager,
    private val bus: AuthEventBus
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 401 && !chain.request().url.encodedPath.startsWith("/api/auth")) {
            CoroutineScope(Dispatchers.IO).launch {
                prefs.clear()
                bus.emitLogout()
            }
        }
        return response
    }
}
