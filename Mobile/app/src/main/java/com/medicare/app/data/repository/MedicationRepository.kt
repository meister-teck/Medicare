package com.medicare.app.data.repository

import com.medicare.app.data.api.MockData
import com.medicare.app.data.api.dto.MedicationDto
import com.medicare.app.data.api.dto.MedicationRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicationRepository @Inject constructor() {

    private var nextId = 3L
    private val medications = MockData.medications.toMutableList()

    suspend fun getMedicationsByCondition(conditionId: Long): List<MedicationDto> {
        return medications.filter { it.conditionId == conditionId }
    }

    suspend fun createMedication(request: MedicationRequest): MedicationDto {
        val newMed = MedicationDto(
            id = nextId++,
            conditionId = request.conditionId,
            name = request.name,
            dosesPerDay = request.dosesPerDay,
            durationDays = request.durationDays,
            startDate = request.startDate,
            createdAt = java.time.LocalDateTime.now().toString()
        )
        medications.add(newMed)
        return newMed
    }

    suspend fun deleteMedication(id: Long) {
        medications.removeAll { it.id == id }
    }
}