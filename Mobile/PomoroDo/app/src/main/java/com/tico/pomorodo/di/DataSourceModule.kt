package com.tico.pomorodo.di

import com.tico.pomorodo.data.local.dao.CalendarDao
import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.dao.TimerDao
import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.datasource.calendar.CalendarLocalDataSource
import com.tico.pomorodo.data.local.datasource.calendar.CalendarLocalDataSourceImpl
import com.tico.pomorodo.data.local.datasource.category.CategoryLocalDataSource
import com.tico.pomorodo.data.local.datasource.category.CategoryLocalDataSourceImpl
import com.tico.pomorodo.data.local.datasource.timer.TimerLocalDataSource
import com.tico.pomorodo.data.local.datasource.timer.TimerLocalDataSourceImpl
import com.tico.pomorodo.data.local.datasource.todo.TodoLocalDataSource
import com.tico.pomorodo.data.local.datasource.todo.TodoLocalDataSourceImpl
import com.tico.pomorodo.data.remote.datasource.AuthDataSource
import com.tico.pomorodo.data.remote.datasource.AuthDataSourceImpl
import com.tico.pomorodo.data.remote.datasource.TodoRemoteDataSource
import com.tico.pomorodo.data.remote.datasource.TodoRemoteDataSourceImpl
import com.tico.pomorodo.data.remote.datasource.TokenDataSource
import com.tico.pomorodo.data.remote.datasource.TokenDataSourceImpl
import com.tico.pomorodo.data.remote.datasource.UserRemoteDataSource
import com.tico.pomorodo.data.remote.datasource.UserRemoteDataSourceImpl
import com.tico.pomorodo.data.remote.service.AuthApiService
import com.tico.pomorodo.data.remote.service.ReissueTokenService
import com.tico.pomorodo.data.remote.service.TodoApiService
import com.tico.pomorodo.data.remote.service.TokenApiService
import com.tico.pomorodo.data.remote.service.UserApiService
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
    fun provideTokenDataSource(
        tokenApiService: TokenApiService,
        reissueTokenService: ReissueTokenService
    ): TokenDataSource =
        TokenDataSourceImpl(tokenApiService, reissueTokenService)

    @Singleton
    @Provides
    fun provideTodoRemoteDataSource(todoApiService: TodoApiService): TodoRemoteDataSource =
        TodoRemoteDataSourceImpl(todoApiService)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(userApiService: UserApiService): UserRemoteDataSource =
        UserRemoteDataSourceImpl(userApiService)

    @Singleton
    @Provides
    fun provideTodoLocalDataSource(todoDao: TodoDao): TodoLocalDataSource =
        TodoLocalDataSourceImpl(todoDao)

    @Singleton
    @Provides
    fun provideCategoryLocalDataSource(categoryDao: CategoryDao): CategoryLocalDataSource =
        CategoryLocalDataSourceImpl(categoryDao)

    @Singleton
    @Provides
    fun provideCalendarLocalDataSource(calendarDao: CalendarDao): CalendarLocalDataSource =
        CalendarLocalDataSourceImpl(calendarDao)

    @Singleton
    @Provides
    fun provideTimerLocalDataSource(timerDao: TimerDao): TimerLocalDataSource =
        TimerLocalDataSourceImpl(timerDao)
}