package com.tico.pomorodo.common.util

import androidx.room.TypeConverter
import com.tico.pomorodo.data.local.entity.UserEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun userListToJson(value: List<UserEntity>?): String? {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        if (value == null) {
            return null
        }
        return json.encodeToString(value)
    }

    @TypeConverter
    fun jsonToUserList(value: String): List<UserEntity>? {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate): Int {
        return date.toEpochDays()
    }

    @TypeConverter
    fun toLocalDate(epochDays: Int): LocalDate {
        return LocalDate.fromEpochDays(epochDays)
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime): Long {
        return time.toMillisecondOfDay().toLong()
    }

    @TypeConverter
    fun toLocalTime(time: Long): LocalTime {
        return LocalTime.fromMillisecondOfDay(time.toInt())
    }

    companion object {
        val TIME_ZONE = TimeZone.currentSystemDefault()
    }

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String {
        return LocalDateTime.Formats.ISO.format(localDateTime)
    }

    @TypeConverter
    fun toLocalDateTime(localDateTime: String): LocalDateTime {
        return LocalDateTime.Formats.ISO.parse(localDateTime)
    }
}