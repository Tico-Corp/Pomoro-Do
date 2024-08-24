package com.tico.pomorodo.data.local.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.TodoEntity

@Database(entities = [TodoEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
abstract class PomorodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: PomorodoDatabase? = null

        fun getDatabase(context: Context): PomorodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PomorodoDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private const val DATABASE_NAME = "pomorodo_database"
    }
}