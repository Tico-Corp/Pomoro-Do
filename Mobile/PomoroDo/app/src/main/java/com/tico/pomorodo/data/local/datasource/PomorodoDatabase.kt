package com.tico.pomorodo.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tico.pomorodo.common.util.Converters
import com.tico.pomorodo.data.local.dao.CalendarDao
import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.dao.TimerDao
import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.entity.CalendarDateEntity
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.TimerSettingEntity
import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.local.entity.UserEntity

@Database(
    entities = [TodoEntity::class, CategoryEntity::class, UserEntity::class, CalendarDateEntity::class, TimerSettingEntity::class],
    version = 10,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PomorodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun categoryDao(): CategoryDao
    abstract fun calendarDao(): CalendarDao
    abstract fun timerDao(): TimerDao

    companion object {
        const val DATABASE_NAME = "pomorodo_database"
    }
}