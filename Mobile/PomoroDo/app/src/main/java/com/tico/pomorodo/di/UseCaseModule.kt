package com.tico.pomorodo.di

import com.tico.pomorodo.domain.repository.AuthRepository
import com.tico.pomorodo.domain.repository.CategoryRepository
import com.tico.pomorodo.domain.repository.TodoRepository
import com.tico.pomorodo.domain.repository.TokenRepository
import com.tico.pomorodo.domain.usecase.ClearAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.ClearIdTokenUseCase
import com.tico.pomorodo.domain.usecase.GetAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.GetAllCategoryUseCase
import com.tico.pomorodo.domain.usecase.GetAllTodoUseCase
import com.tico.pomorodo.domain.usecase.GetIdTokenUseCase
import com.tico.pomorodo.domain.usecase.InsertTodoUseCase
import com.tico.pomorodo.domain.usecase.IsAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.JoinUseCase
import com.tico.pomorodo.domain.usecase.LoginUseCase
import com.tico.pomorodo.domain.usecase.ReissueTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveIdTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveRefreshTokenUseCase
import com.tico.pomorodo.domain.usecase.ValidateTokenUseCase
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
    fun provideSaveRefreshTokenUseCase(authRepository: AuthRepository): SaveRefreshTokenUseCase {
        return SaveRefreshTokenUseCase(authRepository)
    }

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
    fun provideIsAccessTokenUseCase(authRepository: AuthRepository): IsAccessTokenUseCase {
        return IsAccessTokenUseCase(authRepository)
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

    @Singleton
    @Provides
    fun provideValidateTokenUseCase(tokenRepository: TokenRepository): ValidateTokenUseCase {
        return ValidateTokenUseCase(tokenRepository)
    }

    @Singleton
    @Provides
    fun provideReissueTokenUseCase(tokenRepository: TokenRepository): ReissueTokenUseCase {
        return ReissueTokenUseCase(tokenRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllTodoUseCase(todoRepository: TodoRepository): GetAllTodoUseCase {
        return GetAllTodoUseCase(todoRepository)
    }

    @Singleton
    @Provides
    fun provideInsertTodoUseCase(todoRepository: TodoRepository): InsertTodoUseCase {
        return InsertTodoUseCase(todoRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllCategoryUseCase(categoryRepository: CategoryRepository): GetAllCategoryUseCase {
        return GetAllCategoryUseCase(categoryRepository)
    }
}