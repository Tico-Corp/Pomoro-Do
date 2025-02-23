package com.tico.pomorodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.TimerSettingData
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Entity(tableName = "time_setting_table")
@Serializable
data class TimerSettingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "target_focus_info")
    val time: LocalTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

fun TimerSettingEntity.toTimerSettingData() =
    TimerSettingData(id = id, userId = userId, time = time, updatedAt = updatedAt)