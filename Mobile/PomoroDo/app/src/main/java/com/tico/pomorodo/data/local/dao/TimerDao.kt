package com.tico.pomorodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tico.pomorodo.data.local.entity.DailyTimerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConcentrationGoal(dailyTimerEntity: DailyTimerEntity)

    @Query("SELECT * FROM daily_timer_table WHERE user_id = :userId")
    fun getConcentrationGoal(userId: Int): Flow<DailyTimerEntity?>

    @Update
    suspend fun updateTargetFocusTime(dailyTimerEntity: DailyTimerEntity)
}