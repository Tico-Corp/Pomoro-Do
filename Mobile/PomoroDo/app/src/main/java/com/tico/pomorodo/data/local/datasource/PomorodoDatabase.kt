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
import com.tico.pomorodo.data.local.entity.DailyTimerEntity
import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.local.entity.UserEntity

@Database(
    entities = [
        TodoEntity::class,
        CategoryEntity::class,
        UserEntity::class,
        CalendarDateEntity::class,
        DailyTimerEntity::class
    ],
    version = 12,
    exportSchema = false,
//    autoMigrations = [
//        // (https://namneul.tistory.com/36) 참고하여 작성
//        AutoMigration(from = 10, to = 11)
//    ]
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