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
    fun provideSaveAccessTokenUseCase(authRepository: AuthRepository): SaveAccessTokenUseCase {
        return SaveAccessTokenUseCase(authRepository)
    }

    @Provides
    fun provideGetAccessTokenUseCase(authRepository: AuthRepository): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(authRepository)
    }

    @Provides
    fun provideClearAccessTokenUseCase(authRepository: AuthRepository): ClearAccessTokenUseCase {
        return ClearAccessTokenUseCase(authRepository)
    }
}