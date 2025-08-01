package com.ivanpuzyrev.domain

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val error: ApiError) : ApiResult<Nothing>()
}

sealed class ApiError {
    object Network : ApiError()
    data class Api(val code: Int, val message: String?) : ApiError()
    object Unknown : ApiError()
}