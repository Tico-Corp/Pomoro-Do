package com.tico.pomorodo.di

import android.content.Context
import com.tico.pomorodo.data.local.PreferencesManager
import com.tico.pomorodo.data.remote.datasource.AuthDataSource
import com.tico.pomorodo.data.repository.AuthRepositoryImpl
import com.tico.pomorodo.domain.repository.AuthRepository
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
}