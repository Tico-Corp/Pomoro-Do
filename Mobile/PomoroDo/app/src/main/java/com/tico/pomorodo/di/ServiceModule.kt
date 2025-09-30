package com.tico.pomorodo.di

import com.tico.pomorodo.common.util.AccessTokenInterceptorClient
import com.tico.pomorodo.common.util.IdTokenInterceptorClient
import com.tico.pomorodo.common.util.RefreshTokenInterceptorClient
import com.tico.pomorodo.data.remote.service.AuthApiService
import com.tico.pomorodo.data.remote.service.CategoryApiService
import com.tico.pomorodo.data.remote.service.ReissueTokenService
import com.tico.pomorodo.data.remote.service.TodoApiService
import com.tico.pomorodo.data.remote.service.TokenApiService
import com.tico.pomorodo.data.remote.service.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(
        @IdTokenInterceptorClient
        retrofit: Retrofit
    ): AuthApiService = retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideTokenService(
        @AccessTokenInterceptorClient
        retrofit: Retrofit
    ): TokenApiService = retrofit.create(TokenApiService::class.java)

    @Provides
    @Singleton
    fun provideReissueTokenService(
        @RefreshTokenInterceptorClient
        retrofit: Retrofit
    ): ReissueTokenService = retrofit.create(ReissueTokenService::class.java)

    @Provides
    @Singleton
    fun provideTodoService(@AccessTokenInterceptorClient retrofit: Retrofit): TodoApiService =
        retrofit.create(TodoApiService::class.java)

    @Provides
    @Singleton
    fun provideUserService(@AccessTokenInterceptorClient retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideCategoryService(@AccessTokenInterceptorClient retrofit: Retrofit): CategoryApiService =
        retrofit.create(CategoryApiService::class.java)
}