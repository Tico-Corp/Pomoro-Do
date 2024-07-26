package com.tico.pomorodo.data.remote.interceptor

import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.common.util.NetworkConstants
import com.tico.pomorodo.data.local.PreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class IdTokenInterceptor(private val preferencesManager: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                NetworkConstants.GOOGLE_ID_TOKEN_NAME,
                "${BuildConfig.HEADER_PREFIX}${preferencesManager.getIdToken()}"
            ).build()
        return chain.proceed(request)
    }
}