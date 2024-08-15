package com.tico.pomorodo.domain.model

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Loading : Resource<Nothing>()
    sealed class Failure<out T>(val message: String?, val code: String? = null) : Resource<T>() {
        class Error<out T>(message: String?, code: String?) : Failure<T>(message, code)
        class Exception<out T>(message: String?) : Failure<T>(message)
    }
}