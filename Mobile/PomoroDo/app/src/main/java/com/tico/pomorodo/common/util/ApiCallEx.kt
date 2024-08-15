package com.tico.pomorodo.common.util

import android.database.sqlite.SQLiteException
import com.tico.pomorodo.data.remote.models.response.ErrorResponse
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> wrapToResource(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T,
): Resource<T> {
    return try {
        withContext(dispatcher) {
            Resource.Success(apiCall())
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                Resource.Failure.Exception(throwable.message ?: "IOException")
            }

            is HttpException -> {
                val errorResponse = parseErrorResponse(throwable)
                Resource.Failure.Error(errorResponse?.message ?: "HTTP Error", errorResponse?.code)
            }

            is SQLiteException -> {
                Resource.Failure.Exception(throwable.message ?: "SQLiteException")
            }

            else -> {
                Resource.Failure.Exception(throwable.message ?: "Unknown Error")
            }
        }
    }
}

private fun parseErrorResponse(exception: HttpException): ErrorResponse? {
    return try {
        val errorBody = exception.response()?.errorBody()?.string()
        if (errorBody != null) {
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<ErrorResponse>(errorBody)
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}