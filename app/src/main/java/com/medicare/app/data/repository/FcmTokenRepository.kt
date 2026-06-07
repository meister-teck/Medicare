package com.medicare.app.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenRepository @Inject constructor() {

    suspend fun registerToken(token: String, device: String) {
        // Ne fait rien avec les mocks
    }

    suspend fun unregisterToken(token: String) {
        // Ne fait rien avec les mocks
    }
}