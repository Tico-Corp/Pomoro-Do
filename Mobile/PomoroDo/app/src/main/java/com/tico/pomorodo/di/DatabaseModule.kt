package com.tico.pomorodo.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tico.pomorodo.data.local.dao.CalendarDao
import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.dao.TimerDao
import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.datasource.DataSource.INITIAL_CALENDAR_DATA
import com.tico.pomorodo.data.local.datasource.DataSource.INITIAL_CATEGORY_DATA
import com.tico.pomorodo.data.local.datasource.DataSource.INITIAL_TODO_DATA
import com.tico.pomorodo.data.local.datasource.PomorodoDatabase
import com.tico.pomorodo.data.local.datasource.PomorodoDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): PomorodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        PomorodoDatabase::class.java,
        DATABASE_NAME
    ).addCallback(object : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Executors.newSingleThreadExecutor().execute {
                runBlocking {
                    val database = provideAppDatabase(context)
                    // 초기 데이터 설정
                    database.categoryDao().insertAll(INITIAL_CATEGORY_DATA)
                    database.todoDao().insertAll(INITIAL_TODO_DATA)
                    database.calendarDao().insert(INITIAL_CALENDAR_DATA)
                }
            }
        }
    }).build()

    @Singleton
    @Provides
    fun provideTodoDao(pomorodoDatabase: PomorodoDatabase): TodoDao = pomorodoDatabase.todoDao()

    @Singleton
    @Provides
    fun provideCategoryDao(pomorodoDatabase: PomorodoDatabase): CategoryDao =
        pomorodoDatabase.categoryDao()

    @Singleton
    @Provides
    fun provideCalendarDao(pomorodoDatabase: PomorodoDatabase): CalendarDao =
        pomorodoDatabase.calendarDao()

    @Singleton
    @Provides
    fun provideTimerDao(pomorodoDatabase: PomorodoDatabase): TimerDao =
        pomorodoDatabase.timerDao()
}