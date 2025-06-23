package com.tico.pomorodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tico.pomorodo.data.local.entity.TimerSettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConcentrationGoal(timerSettingEntity: TimerSettingEntity)

    @Query("SELECT * FROM time_setting_table WHERE user_id = :userId")
    fun getConcentrationGoal(userId: Int): Flow<TimerSettingEntity?>

    @Update
    suspend fun updateConcentrationGoal(timerSettingEntity: TimerSettingEntity)
}