package com.medicare.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.medicare.app.data.local.entities.DoseEntity

@Database(entities = [DoseEntity::class], version = 1, exportSchema = false)
abstract class MediCareDatabase : RoomDatabase() {
    abstract fun doseDao(): DoseDao
}
