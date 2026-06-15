package com.medicare.app.data.repository

import com.medicare.app.data.api.MockData
import com.medicare.app.data.api.dto.DoseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoseRepository @Inject constructor() {

    private val doses = MockData.todayDoses.toMutableList()

    suspend fun getTodayDoses(): List<DoseDto> {
        return doses.toList()
    }

    suspend fun getDosesByMedication(medicationId: Long): List<DoseDto> {
        return doses.filter { it.medicationId == medicationId }
    }

    suspend fun markDoseAsTaken(doseId: Long): DoseDto {
        val index = doses.indexOfFirst { it.id == doseId }
        if (index >= 0) {
            doses[index] = doses[index].copy(
                taken = true,
                takenTimestamp = java.time.LocalDateTime.now().toString()
            )
            return doses[index]
        }
        return doses.first()
    }

    suspend fun updateNote(doseId: Long, notes: String): DoseDto {
        val index = doses.indexOfFirst { it.id == doseId }
        if (index >= 0) {
            doses[index] = doses[index].copy(notes = notes)
            return doses[index]
        }
        return doses.first()
    }
}