package com.tico.pomorodo.di

import com.tico.pomorodo.domain.repository.AuthRepository
import com.tico.pomorodo.domain.repository.CategoryRepository
import com.tico.pomorodo.domain.repository.TodoRepository
import com.tico.pomorodo.domain.repository.TokenRepository
import com.tico.pomorodo.domain.usecase.auth.ClearAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.ClearIdTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.GetAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.GetIdTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.IsAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.JoinUseCase
import com.tico.pomorodo.domain.usecase.auth.LoginUseCase
import com.tico.pomorodo.domain.usecase.auth.ReissueTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.SaveAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.SaveIdTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.SaveRefreshTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.ValidateTokenUseCase
import com.tico.pomorodo.domain.usecase.category.GetAllCategoryUseCase
import com.tico.pomorodo.domain.usecase.category.GetCategoryInfoUseCase
import com.tico.pomorodo.domain.usecase.category.InsertCategoryUseCase
import com.tico.pomorodo.domain.usecase.todo.GetAllTodoUseCase
import com.tico.pomorodo.domain.usecase.todo.InsertTodoUseCase
import com.tico.pomorodo.domain.usecase.category.GetAllCategoryUseCase
import com.tico.pomorodo.domain.usecase.todo.InsertTodoUseCase
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
    fun provideInsertTodoUseCase(todoRepository: TodoRepository): InsertTodoUseCase {
        return InsertTodoUseCase(todoRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllCategoryUseCase(categoryRepository: CategoryRepository): GetAllCategoryUseCase {
        return GetAllCategoryUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideGetCategoryInfoUseCase(categoryRepository: CategoryRepository): GetCategoryInfoUseCase {
        return GetCategoryInfoUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideInsertCategoryUseCase(categoryRepository: CategoryRepository): InsertCategoryUseCase {
        return InsertCategoryUseCase(categoryRepository)
    }
}