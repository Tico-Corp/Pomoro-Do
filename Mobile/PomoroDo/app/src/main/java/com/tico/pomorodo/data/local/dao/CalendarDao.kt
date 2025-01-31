package com.tico.pomorodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tico.pomorodo.data.local.entity.CalendarDateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface CalendarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(calendarDateEntity: CalendarDateEntity)

    @Query("SELECT * FROM calendar_table WHERE date BETWEEN :startEpochDays AND :endEpochDays")
    fun getCalendarDateForMonth(
        startEpochDays: Int,
        endEpochDays: Int
    ): Flow<List<CalendarDateEntity>>

    @Update
    suspend fun updateCalendarDateForMonth(calendarDateEntity: CalendarDateEntity)

    @Query("DELETE FROM calendar_table WHERE date = :date")
    suspend fun deleteCalendarDateForMonth(date: LocalDate)
}