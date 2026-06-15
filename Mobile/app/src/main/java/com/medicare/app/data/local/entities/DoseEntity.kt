package com.medicare.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doses")
data class DoseEntity(
    @PrimaryKey val id: Long,
    val medicationId: Long,
    val medicationName: String,
    val doseDate: String,
    val doseIndex: Int,
    val scheduledTime: String,
    val takenTimestamp: String?,
    val taken: Boolean,
    val notes: String?
)
