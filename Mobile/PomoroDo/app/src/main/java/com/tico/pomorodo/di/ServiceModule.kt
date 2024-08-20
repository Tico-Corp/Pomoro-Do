package com.tico.pomorodo.di

import com.tico.pomorodo.common.util.AccessTokenInterceptorClient
import com.tico.pomorodo.common.util.IdTokenInterceptorClient
import com.tico.pomorodo.data.remote.service.AuthApiService
import com.tico.pomorodo.data.remote.service.TodoApiService
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
    fun provideTodoService(@AccessTokenInterceptorClient retrofit: Retrofit): TodoApiService =
        retrofit.create(TodoApiService::class.java)
}