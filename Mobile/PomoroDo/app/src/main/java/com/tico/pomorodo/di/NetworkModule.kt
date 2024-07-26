package com.tico.pomorodo.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.common.util.AuthInterceptorClient
import com.tico.pomorodo.common.util.IdTokenInterceptorClient
import com.tico.pomorodo.data.local.PreferencesManager
import com.tico.pomorodo.data.remote.interceptor.AuthInterceptor
import com.tico.pomorodo.data.remote.interceptor.IdTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideHttpClient(preferencesManager: PreferencesManager): OkHttpClient {
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
    @AuthInterceptorClient
    fun provideAuthHttpClient(
        preferencesManager: PreferencesManager
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val authInterceptor = AuthInterceptor(preferencesManager)
        return OkHttpClient.Builder()
            .readTimeout(TIME, TimeUnit.SECONDS)
            .connectTimeout(TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @IdTokenInterceptorClient
    fun provideRetrofit(
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
    @AuthInterceptorClient
    fun provideAuthRetrofit(
        @AuthInterceptorClient
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
}