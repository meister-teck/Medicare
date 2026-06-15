package com.medicare.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicare.app.data.repository.AuthRepository
import com.medicare.app.util.AuthEventBus
import com.medicare.app.util.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    prefs: PreferencesManager,
    bus: AuthEventBus
) : ViewModel() {

    val token: StateFlow<String?> = prefs.tokenFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val logoutEvents = bus.logout

    suspend fun login(email: String, password: String) = repo.login(email, password)
    suspend fun register(email: String, password: String) = repo.register(email, password)
    fun logout() = viewModelScope.launch { repo.logout() }
}
