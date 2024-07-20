package com.tico.pomorodo.di

import com.tico.pomorodo.data.remote.datasource.AuthDataSource
import com.tico.pomorodo.data.remote.datasource.AuthDataSourceImpl
import com.tico.pomorodo.data.remote.service.AuthApiService
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
}