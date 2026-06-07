package com.medicare.app.data.api.dto

// Auth
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val email: String, val password: String)
data class AuthResponse(val token: String, val userId: Long)

// Condition
data class ConditionRequest(val type: String)
data class ConditionDto(
    val id: Long,
    val type: String,
    val name: String = "",
    val createdAt: String
)

// Medication
data class MedicationRequest(
    val conditionId: Long,
    val name: String,
    val dosesPerDay: Int,
    val durationDays: Int,
    val startDate: String,
    val scheduledTimes: List<String>
)
data class MedicationDto(
    val id: Long,
    val conditionId: Long,
    val name: String,
    val dosesPerDay: Int,
    val durationDays: Int,
    val startDate: String,
    val createdAt: String
)

// Dose
data class DoseDto(
    val id: Long,
    val medicationId: Long,
    val medicationName: String,
    val doseDate: String,
    val doseIndex: Int,
    val scheduledTime: String?,
    val takenTimestamp: String?,
    val taken: Boolean,
    val notes: String?
)
data class NoteRequest(val notes: String)

// FCM
data class FcmTokenRequest(val token: String, val device: String)