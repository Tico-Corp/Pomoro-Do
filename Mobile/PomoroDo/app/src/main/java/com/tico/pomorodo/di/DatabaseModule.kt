package com.tico.pomorodo.di

import android.content.Context
import androidx.room.Room
import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.datasource.PomorodoDatabase
import com.tico.pomorodo.data.local.datasource.PomorodoDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    ).build()

    @Singleton
    @Provides
    fun provideTodoDao(pomorodoDatabase: PomorodoDatabase): TodoDao = pomorodoDatabase.todoDao()

    @Singleton
    @Provides
    fun provideCategoryDao(pomorodoDatabase: PomorodoDatabase): CategoryDao =
        pomorodoDatabase.categoryDao()
}