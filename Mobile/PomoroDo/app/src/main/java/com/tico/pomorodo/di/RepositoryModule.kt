package com.tico.pomorodo.di

import android.content.Context
import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.data.local.PreferencesManager
import com.tico.pomorodo.data.local.datasource.calendar.CalendarLocalDataSource
import com.tico.pomorodo.data.local.datasource.category.CategoryLocalDataSource
import com.tico.pomorodo.data.local.datasource.todo.TodoLocalDataSource
import com.tico.pomorodo.data.remote.datasource.AuthDataSource
import com.tico.pomorodo.data.remote.datasource.TodoRemoteDataSource
import com.tico.pomorodo.data.remote.datasource.TokenDataSource
import com.tico.pomorodo.data.repository.AuthRepositoryImpl
import com.tico.pomorodo.data.repository.CalendarRepositoryImpl
import com.tico.pomorodo.data.repository.CategoryRepositoryImpl
import com.tico.pomorodo.data.repository.TodoRepositoryImpl
import com.tico.pomorodo.data.repository.TokenRepositoryImpl
import com.tico.pomorodo.domain.repository.AuthRepository
import com.tico.pomorodo.domain.repository.CalendarRepository
import com.tico.pomorodo.domain.repository.CategoryRepository
import com.tico.pomorodo.domain.repository.TodoRepository
import com.tico.pomorodo.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        preferencesManager: PreferencesManager,
        authDataSource: AuthDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(preferencesManager, authDataSource)
    }

    @Provides
    @Singleton
    fun provideTokenRepository(
        tokenDataSource: TokenDataSource
    ): TokenRepository {
        return TokenRepositoryImpl(tokenDataSource)
    }

    @Provides
    @Singleton
    fun provideTodoRepository(
        todoLocalDataSource: TodoLocalDataSource,
        todoRemoteDataSource: TodoRemoteDataSource,
        networkHelper: NetworkHelper
    ): TodoRepository {
        return TodoRepositoryImpl(todoLocalDataSource, todoRemoteDataSource, networkHelper)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryLocalDataSource: CategoryLocalDataSource,
        networkHelper: NetworkHelper
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryLocalDataSource, networkHelper)
    }

    @Provides
    @Singleton
    fun provideCalendarRepository(
        categoryLocalDataSource: CalendarLocalDataSource,
        networkHelper: NetworkHelper
    ): CalendarRepository {
        return CalendarRepositoryImpl(categoryLocalDataSource, networkHelper)
    }
}