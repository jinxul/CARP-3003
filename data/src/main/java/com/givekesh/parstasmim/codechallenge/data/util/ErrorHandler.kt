package com.givekesh.parstasmim.codechallenge.data.util

import android.util.Log
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.ResultMessageResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

internal fun HttpException.parseApiError(): ApiResult.ApiError {
    val unexpectedError = "Unexpected Error Occurred."
    return try {
        val jsonBody = response()?.errorBody()?.string() ?: ""
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        moshi.adapter(ResultMessageResponse::class.java).fromJson(jsonBody)
            .let { ApiResult.ApiError(it!!.message) }
    } catch (e: Exception) {
        Log.e("TAG", "$e")
        ApiResult.ApiError(errorMessage = unexpectedError)
    }
}

internal fun Throwable.parseNetworkError(): ApiResult.ApiException = when (this) {
    is SSLHandshakeException,
    is SocketTimeoutException,
    is UnknownHostException,
    is ConnectException,
    is IOException -> ApiResult.ApiException(
        Throwable("Network Error")
    )

    else -> ApiResult.ApiException(
        Throwable("Unexpected Error Occurred.")
    )
}