package com.medicare.app.ui.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicare.app.data.api.dto.MedicationDto
import com.medicare.app.data.api.dto.MedicationRequest
import com.medicare.app.data.repository.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {

    private val _medications = MutableStateFlow<List<MedicationDto>>(emptyList())
    val medications: StateFlow<List<MedicationDto>> = _medications

    fun loadMedications(conditionId: Long) {
        viewModelScope.launch {
            _medications.value = repository.getMedicationsByCondition(conditionId)
        }
    }

    fun addMedication(request: MedicationRequest) {
        viewModelScope.launch {
            repository.createMedication(request)
        }
    }

    fun deleteMedication(id: Long) {
        viewModelScope.launch {
            repository.deleteMedication(id)
        }
    }
}