package com.medicare.app.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AuthEventBus {
    private val _logout = MutableSharedFlow<Unit>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val logout: SharedFlow<Unit> = _logout
    suspend fun emitLogout() { _logout.emit(Unit) }
}
