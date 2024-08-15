package com.tico.pomorodo.data.remote.interceptor

import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.common.util.NetworkConstants
import com.tico.pomorodo.data.local.PreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val preferences: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                NetworkConstants.TOKEN_NAME,
                "${BuildConfig.HEADER_PREFIX}${preferences.getAccessToken()}"
            ).build()
        return chain.proceed(request)
    }
}