package com.medicare.app.data.api

import com.medicare.app.data.api.dto.*
import retrofit2.http.*

interface MediCareApi {
    // Auth
    @POST("api/auth/register")
    suspend fun register(@Body body: RegisterRequest)

    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): AuthResponse

    // Conditions
    @GET("api/conditions")
    suspend fun getConditions(): List<ConditionDto>

    @POST("api/conditions")
    suspend fun createCondition(@Body body: ConditionRequest): ConditionDto

    @GET("api/conditions/{id}")
    suspend fun getCondition(@Path("id") id: Long): ConditionDto

    @PUT("api/conditions/{id}")
    suspend fun updateCondition(@Path("id") id: Long, @Body body: ConditionRequest): ConditionDto

    @DELETE("api/conditions/{id}")
    suspend fun deleteCondition(@Path("id") id: Long)

    // Medications
    @POST("api/medications")
    suspend fun createMedication(@Body body: MedicationRequest): MedicationDto

    @GET("api/medications/condition/{conditionId}")
    suspend fun getMedicationsByCondition(@Path("conditionId") conditionId: Long): List<MedicationDto>

    @GET("api/medications/{id}")
    suspend fun getMedication(@Path("id") id: Long): MedicationDto

    @PUT("api/medications/{id}")
    suspend fun updateMedication(@Path("id") id: Long, @Body body: MedicationRequest): MedicationDto

    @DELETE("api/medications/{id}")
    suspend fun deleteMedication(@Path("id") id: Long)

    // Doses
    @GET("api/doses/today")
    suspend fun getTodayDoses(): List<DoseDto>

    @GET("api/doses/medication/{medicationId}")
    suspend fun getDosesByMedication(@Path("medicationId") medicationId: Long): List<DoseDto>

    @GET("api/doses/medication/{medicationId}/date")
    suspend fun getDosesByMedicationAndDate(
        @Path("medicationId") medicationId: Long,
        @Query("date") date: String
    ): List<DoseDto>

    @GET("api/doses/date")
    suspend fun getDosesByDate(@Query("date") date: String): List<DoseDto>

    @PATCH("api/doses/{doseId}/take")
    suspend fun takeDose(@Path("doseId") doseId: Long): DoseDto

    @PATCH("api/doses/{doseId}/note")
    suspend fun addNote(@Path("doseId") doseId: Long, @Body body: NoteRequest): DoseDto

    // FCM
    @POST("api/user/fcm-token")
    suspend fun registerFcmToken(@Body body: FcmTokenRequest)

    @HTTP(method = "DELETE", path = "api/user/fcm-token", hasBody = true)
    suspend fun deleteFcmToken(@Body body: FcmTokenRequest)
}
