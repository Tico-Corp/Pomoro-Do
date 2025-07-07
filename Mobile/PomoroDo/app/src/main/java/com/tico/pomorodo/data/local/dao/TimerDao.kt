package com.tico.pomorodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tico.pomorodo.data.local.entity.DailyTimerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConcentrationGoal(dailyTimerEntity: DailyTimerEntity)

    @Query("SELECT * FROM daily_timer_table WHERE user_id = :userId AND stat_date = :statDate")
    fun getDailyTimerData(userId: Int, statDate: LocalDate): Flow<DailyTimerEntity?>

    @Update
    suspend fun updateTargetFocusTime(dailyTimerEntity: DailyTimerEntity)
}