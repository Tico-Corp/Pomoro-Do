package com.tico.pomorodo.data.local.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.model.CategoryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    "pomorodo_database"
                )
                    .addCallback(TodoDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class TodoDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        database.categoryDao().insertAll(prepopulateCategories())
                    }
                }
            }
        }

        private fun prepopulateCategories(): List<CategoryEntity> {
            return listOf(
                CategoryEntity(id = 1, title = "카테고리 1", type = CategoryType.NORMAL),
                CategoryEntity(id = 2, title = "카테고리 2", type = CategoryType.NORMAL),
                CategoryEntity(id = 3, title = "카테고리 3", type = CategoryType.NORMAL)
            )
        }
    }
}