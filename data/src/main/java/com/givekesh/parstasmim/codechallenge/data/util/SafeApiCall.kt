package com.givekesh.parstasmim.codechallenge.data.util

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response

internal suspend fun <T> safeApiCall(
    execute: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResult.ApiSuccess(body)
        } else {
            throw HttpException(response)
        }
    } catch (e: HttpException) {
        e.parseApiError()
    } catch (e: Throwable) {
        Log.e("error", "$e", e)
        e.parseNetworkError()
    }
}