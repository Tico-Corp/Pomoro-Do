package com.tico.pomorodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.data.model.CalendarFocusState
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Entity(tableName = "calendar_table")
@Serializable
data class CalendarDateEntity(
    @PrimaryKey
    val date: LocalDate,
    @ColumnInfo(name = "focus_state")
    val focusState: CalendarFocusState = CalendarFocusState.WHITE,
    @ColumnInfo(name = "remained_todo_count")
    val remainedTodoCount: Int = 0,
    @ColumnInfo(name = "total_count")
    val totalCount: Int = 0,
)

fun CalendarDateEntity.toCalendarDate() = CalendarDate(
    date = date,
    focusState = focusState,
    remainedTodoCount = remainedTodoCount,
    totalCount = totalCount
)