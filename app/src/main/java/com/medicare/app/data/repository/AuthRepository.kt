package com.medicare.app.data.repository

import com.medicare.app.data.api.MockData
import com.medicare.app.util.PreferencesManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val prefs: PreferencesManager
) {
    suspend fun login(email: String, password: String) {
        val resp = MockData.loginResponse
        prefs.saveSession(resp.token, resp.userId, email)
    }

    suspend fun register(email: String, password: String) {
        // Ne fait rien avec les mocks
    }

    suspend fun logout() = prefs.clear()
}
