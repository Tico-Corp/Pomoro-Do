package com.tico.pomorodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.tico.pomorodo.data.model.TimerSettingData
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Entity(tableName = "time_setting_table", primaryKeys = ["user_id"])
@Serializable
data class TimerSettingEntity(
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "target_focus_info")
    val time: LocalTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

fun TimerSettingEntity.toTimerSettingData() =
    TimerSettingData(userId = userId, time = time, updatedAt = updatedAt)