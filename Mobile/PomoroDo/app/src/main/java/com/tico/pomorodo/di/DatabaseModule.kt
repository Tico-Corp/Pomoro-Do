package com.tico.pomorodo.di

import android.content.Context
import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.datasource.PomorodoDatabase
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
    ): PomorodoDatabase = PomorodoDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideTodoDao(pomorodoDatabase: PomorodoDatabase): TodoDao = pomorodoDatabase.todoDao()

    @Singleton
    @Provides
    fun provideCategoryDao(pomorodoDatabase: PomorodoDatabase): CategoryDao =
        pomorodoDatabase.categoryDao()

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
}