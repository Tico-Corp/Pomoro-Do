package com.tico.pomorodo.di

import com.tico.pomorodo.domain.repository.AuthRepository
import com.tico.pomorodo.domain.usecase.ClearTokenUseCase
import com.tico.pomorodo.domain.usecase.GetTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideSaveTokenUseCase(authRepository: AuthRepository): SaveTokenUseCase {
        return SaveTokenUseCase(authRepository)
    }

    @Provides
    fun provideGetTokenUseCase(authRepository: AuthRepository): GetTokenUseCase {
        return GetTokenUseCase(authRepository)
    }

    @Provides
    fun provideClearTokenUseCase(authRepository: AuthRepository): ClearTokenUseCase {
        return ClearTokenUseCase(authRepository)
    }
}