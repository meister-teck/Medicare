package com.medicare.app.ui.conditions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicare.app.data.api.dto.ConditionDto
import com.medicare.app.data.repository.ConditionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConditionViewModel @Inject constructor(
    private val repository: ConditionRepository
) : ViewModel() {

    private val _conditions = MutableStateFlow<List<ConditionDto>>(emptyList())
    val conditions: StateFlow<List<ConditionDto>> = _conditions

    init {
        loadConditions()
    }

    fun loadConditions() {
        viewModelScope.launch {
            _conditions.value = repository.getConditions()
        }
    }

    fun addCondition(type: String, name: String) {
        viewModelScope.launch {
            repository.createCondition(type, name)
            loadConditions()
        }
    }

    fun deleteCondition(id: Long) {
        viewModelScope.launch {
            repository.deleteCondition(id)
            loadConditions()
        }
    }
}