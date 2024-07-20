package com.tico.pomorodo.di

import com.tico.pomorodo.domain.repository.AuthRepository
import com.tico.pomorodo.domain.usecase.ClearAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.ClearIdTokenUseCase
import com.tico.pomorodo.domain.usecase.GetAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.GetIdTokenUseCase
import com.tico.pomorodo.domain.usecase.JoinUseCase
import com.tico.pomorodo.domain.usecase.LoginUseCase
import com.tico.pomorodo.domain.usecase.SaveAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveIdTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideSaveAccessTokenUseCase(authRepository: AuthRepository): SaveAccessTokenUseCase {
        return SaveAccessTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideGetAccessTokenUseCase(authRepository: AuthRepository): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideClearAccessTokenUseCase(authRepository: AuthRepository): ClearAccessTokenUseCase {
        return ClearAccessTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideSaveIdTokenUseCase(authRepository: AuthRepository): SaveIdTokenUseCase {
        return SaveIdTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideGetIdTokenUseCase(authRepository: AuthRepository): GetIdTokenUseCase {
        return GetIdTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideClearIdTokenUseCase(authRepository: AuthRepository): ClearIdTokenUseCase {
        return ClearIdTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideJoinUseCase(authRepository: AuthRepository): JoinUseCase {
        return JoinUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
}