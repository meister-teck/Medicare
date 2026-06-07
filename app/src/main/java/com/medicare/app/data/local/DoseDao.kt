package com.medicare.app.data.local

import androidx.room.*
import com.medicare.app.data.local.entities.DoseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoseDao {
    @Query("SELECT * FROM doses WHERE doseDate = :date ORDER BY scheduledTime")
    fun observeByDate(date: String): Flow<List<DoseEntity>>

    @Query("SELECT * FROM doses WHERE doseDate = :date ORDER BY scheduledTime")
    suspend fun getByDate(date: String): List<DoseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<DoseEntity>)

    @Query("DELETE FROM doses WHERE doseDate = :date")
    suspend fun deleteByDate(date: String)

    @Query("DELETE FROM doses")
    suspend fun clear()
}
