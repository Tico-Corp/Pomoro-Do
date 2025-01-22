package com.tico.pomorodo.common.util

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): Int {
        return date.toEpochDays()
    }

    @TypeConverter
    fun toLocalDate(epochDays: Int): LocalDate {
        return LocalDate.fromEpochDays(epochDays)
    }
}
