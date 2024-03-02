package com.givekesh.parstasmim.codechallenge.data.util

sealed class ApiResult<out R> {
    class ApiSuccess<T>(val data: T) : ApiResult<T>()
    class ApiError(val errorMessage: String) : ApiResult<Nothing>()
    class ApiException(val exception: Throwable) : ApiResult<Nothing>()
}
