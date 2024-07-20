package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.request.UserInfoRequestBody
import com.tico.pomorodo.data.remote.models.response.AuthResponse
import com.tico.pomorodo.data.remote.service.AuthApiService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthDataSource {
    override suspend fun requestLogin(): AuthResponse =
        authApiService.requestLogin()

    override suspend fun requestJoin(userInfo: UserInfoRequestBody, image: File?): AuthResponse {
        val memberJson = Json.encodeToString(userInfo)
        val memberRequestBody = memberJson.toRequestBody("application/json".toMediaTypeOrNull())

        image?.let {
            val requestFile = image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val multipart =
                MultipartBody.Part.createFormData("multipartFile", image.name, requestFile)
            return authApiService.requestJoin(memberRequestBody, multipart)
        }
        return authApiService.requestJoin(memberRequestBody, null)
    }
}