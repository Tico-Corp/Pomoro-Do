package com.tico.pomorodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.DailyTimerData
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Entity(tableName = "daily_timer_table")
@Serializable
data class DailyTimerEntity(
    @PrimaryKey
    @ColumnInfo(name = "stat_date")
    val statDate: LocalDate,
    @ColumnInfo(name = "target_focus_seconds")
    var targetFocusTime: LocalTime,
    @ColumnInfo(name = "total_focus_seconds")
    var totalFocusTime: LocalTime,
    @ColumnInfo(name = "total_break_seconds")
    var totalBreakTime: LocalTime,
    @ColumnInfo(name = "max_focus_seconds")
    var maxFocusTime: LocalTime,
    @ColumnInfo(name = "max_break_time")
    var maxBreakTime: LocalTime,
    @ColumnInfo(name = "incomplete_todos_count")
    var inCompleteTodosCount: Int,
    @ColumnInfo(name = "updated_at")
    var updatedAt: Long = System.currentTimeMillis()
)

fun DailyTimerEntity.toDailyTimerData() =
    DailyTimerData(
        statDate = statDate,
        targetFocusTime = targetFocusTime,
        totalFocusTime = totalFocusTime,
        totalBreakTime = totalBreakTime,
        maxFocusTime = maxFocusTime,
        maxBreakTime = maxBreakTime,
        inCompleteTodosCount = inCompleteTodosCount,
        updatedAt = updatedAt
    )