package com.medicare.app.data.repository

import com.medicare.app.data.api.MockData
import com.medicare.app.data.api.dto.ConditionDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConditionRepository @Inject constructor() {

    private var nextId = 3L

    suspend fun getConditions(): List<ConditionDto> {
        return MockData.conditions.toList()
    }

    suspend fun createCondition(type: String, name: String): ConditionDto {
        val newCondition = ConditionDto(
            id = nextId++,
            type = type,
            name = name,
            createdAt = java.time.LocalDateTime.now().toString()
        )
        MockData.conditions.add(newCondition)
        return newCondition
    }

    suspend fun deleteCondition(id: Long) {
        MockData.conditions.removeAll { it.id == id }
    }
}