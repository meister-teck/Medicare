package com.medicare.app.ui.doses

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
class DoseViewModel @Inject constructor(
    private val repository: DoseRepository
) : ViewModel() {

    private val _todayDoses = MutableStateFlow<List<DoseDto>>(emptyList())
    val todayDoses: StateFlow<List<DoseDto>> = _todayDoses

    private val _historyDoses = MutableStateFlow<List<DoseDto>>(emptyList())
    val historyDoses: StateFlow<List<DoseDto>> = _historyDoses

    init {
        loadTodayDoses()
    }

    fun loadTodayDoses() {
        viewModelScope.launch {
            _todayDoses.value = repository.getTodayDoses()
        }
    }

    fun markAsTaken(doseId: Long) {
        viewModelScope.launch {
            repository.markDoseAsTaken(doseId)
            loadTodayDoses()
        }
    }

    fun updateNote(doseId: Long, notes: String) {
        viewModelScope.launch {
            repository.updateNote(doseId, notes)
            loadTodayDoses()
        }
    }

    fun loadHistory(medicationId: Long) {
        viewModelScope.launch {
            _historyDoses.value = repository.getDosesByMedication(medicationId)
        }
    }
}