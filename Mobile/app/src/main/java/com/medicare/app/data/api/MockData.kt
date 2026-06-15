package com.medicare.app.data.api

import com.medicare.app.data.api.dto.*

object MockData {

    val loginResponse = AuthResponse(token = "mock_token_123", userId = 1)

    val conditions = mutableListOf(
        ConditionDto(id = 1, type = "CHRONIC", name = "Hypertension", createdAt = "2026-05-15T10:00:00"),
        ConditionDto(id = 2, type = "ACUTE", name = "Grippe", createdAt = "2026-05-18T14:30:00")
    )

    val medications = mutableListOf(
        MedicationDto(id = 1, conditionId = 1, name = "Doliprane", dosesPerDay = 3, durationDays = 5, startDate = "2026-05-20", createdAt = "2026-05-20T08:00:00"),
        MedicationDto(id = 2, conditionId = 1, name = "Amoxicilline", dosesPerDay = 2, durationDays = 7, startDate = "2026-05-19", createdAt = "2026-05-19T09:00:00")
    )

    val todayDoses = mutableListOf(
        DoseDto(id = 1, medicationId = 1, medicationName = "Doliprane", doseDate = "2026-05-20", doseIndex = 1, scheduledTime = "2026-05-20T08:00:00", takenTimestamp = null, taken = false, notes = null),
        DoseDto(id = 2, medicationId = 1, medicationName = "Doliprane", doseDate = "2026-05-20", doseIndex = 2, scheduledTime = "2026-05-20T13:00:00", takenTimestamp = "2026-05-20T13:05:00", taken = true, notes = null),
        DoseDto(id = 3, medicationId = 1, medicationName = "Doliprane", doseDate = "2026-05-20", doseIndex = 3, scheduledTime = "2026-05-20T20:00:00", takenTimestamp = null, taken = false, notes = null)
    )

    val historyDoses = todayDoses.toList()
}