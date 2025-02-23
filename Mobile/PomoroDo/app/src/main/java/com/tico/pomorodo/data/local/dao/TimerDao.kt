package com.tico.pomorodo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.tico.pomorodo.data.local.entity.TimerSettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Query("SELECT * FROM time_setting_table WHERE user_id = :userId")
    suspend fun getTargetTime(userId: Int): Flow<TimerSettingEntity>

    @Update
    suspend fun updateTargetTime(timerSettingEntity: TimerSettingEntity)
}