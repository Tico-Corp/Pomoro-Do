package com.tico.pomorodo.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.common.util.AccessTokenInterceptorClient
import com.tico.pomorodo.common.util.IdTokenInterceptorClient
import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.common.util.RefreshTokenInterceptorClient
import com.tico.pomorodo.data.local.PreferencesManager
import com.tico.pomorodo.data.remote.interceptor.AccessTokenInterceptor
import com.tico.pomorodo.data.remote.interceptor.IdTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIME = 10L

    @Provides
    @Singleton
    @IdTokenInterceptorClient
    fun provideIdTokenHttpClient(preferencesManager: PreferencesManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val idTokenInterceptor = IdTokenInterceptor(preferencesManager)
        return OkHttpClient.Builder()
            .readTimeout(TIME, TimeUnit.SECONDS)
            .connectTimeout(TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(idTokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @AccessTokenInterceptorClient
    fun provideAccessTokenHttpClient(
        preferencesManager: PreferencesManager
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val accessTokenInterceptor = AccessTokenInterceptor(preferencesManager)
        return OkHttpClient.Builder()
            .readTimeout(TIME, TimeUnit.SECONDS)
            .connectTimeout(TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(accessTokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @IdTokenInterceptorClient
    fun provideIdTokenRetrofit(
        @IdTokenInterceptorClient
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @RefreshTokenInterceptorClient
    fun provideRefreshTokenRetrofit(
        @RefreshTokenInterceptorClient
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @AccessTokenInterceptorClient
    fun provideAuthRetrofit(
        @AccessTokenInterceptorClient
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
}