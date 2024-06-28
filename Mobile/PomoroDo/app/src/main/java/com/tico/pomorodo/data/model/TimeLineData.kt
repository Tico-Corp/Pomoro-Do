package com.tico.pomorodo.data.model

import java.time.LocalDateTime

data class TimeLineData(
    val start: LocalDateTime,
    val end: LocalDateTime,
    val type: TimeLineType,
    val list: List<TodoData>? = null,
)

enum class TimeLineType {
    FOCUS, BREAK, OUTSIDE
}