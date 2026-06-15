package com.medicare.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicare.app.data.api.dto.DoseDto
import com.medicare.app.data.repository.DoseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val doseRepository: DoseRepository
) : ViewModel() {

    private val _todayDoses = MutableStateFlow<List<DoseDto>>(emptyList())
    val todayDoses: StateFlow<List<DoseDto>> = _todayDoses

    init {
        loadTodayDoses()
    }

    fun loadTodayDoses() {
        viewModelScope.launch {
            _todayDoses.value = doseRepository.getTodayDoses()
        }
    }
}