package com.ivanpuzyrev.data

import com.ivanpuzyrev.domain.ApiError
import com.ivanpuzyrev.domain.ApiResult
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: IOException) {
        ApiResult.Error(ApiError.Network)
    } catch (e: Exception) {
        ApiResult.Error(ApiError.Unknown)
    }
}