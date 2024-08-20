package com.tico.pomorodo.di

import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.datasource.CategoryLocalDataSource
import com.tico.pomorodo.data.local.datasource.CategoryLocalDataSourceImpl
import com.tico.pomorodo.data.local.datasource.TodoLocalDataSource
import com.tico.pomorodo.data.local.datasource.TodoLocalDataSourceImpl
import com.tico.pomorodo.data.remote.datasource.AuthDataSource
import com.tico.pomorodo.data.remote.datasource.AuthDataSourceImpl
import com.tico.pomorodo.data.remote.datasource.TodoRemoteDataSource
import com.tico.pomorodo.data.remote.datasource.TodoRemoteDataSourceImpl
import com.tico.pomorodo.data.remote.service.AuthApiService
import com.tico.pomorodo.data.remote.service.TodoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideAuthDataSource(authApiService: AuthApiService): AuthDataSource =
        AuthDataSourceImpl(authApiService)

    @Singleton
    @Provides
    fun provideTodoRemoteDataSource(todoApiService: TodoApiService): TodoRemoteDataSource =
        TodoRemoteDataSourceImpl(todoApiService)

    @Singleton
    @Provides
    fun provideTodoLocalDataSource(todoDao: TodoDao): TodoLocalDataSource =
        TodoLocalDataSourceImpl(todoDao)

    @Singleton
    @Provides
    fun provideCategoryLocalDataSource(categoryDao: CategoryDao): CategoryLocalDataSource =
        CategoryLocalDataSourceImpl(categoryDao)
}