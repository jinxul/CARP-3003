package com.givekesh.parstasmim.codechallenge.domain.util

import android.util.Log
import com.givekesh.parstasmim.codechallenge.data.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

internal inline fun <reified DomainResponseType, ApiResponseType> safeFlow(
    noinline apiCall: suspend () -> ApiResult<ApiResponseType>,
    noinline block: suspend FlowCollector<DataState<DomainResponseType>>.(
        apiResponse: ApiResponseType
    ) -> Unit
): Flow<DataState<DomainResponseType>> = flow {
    emit(DataState.Loading)
    when (val result = apiCall.invoke()) {
        is ApiResult.ApiSuccess -> block.invoke(this, result.data)
        is ApiResult.ApiError -> emit(DataState.Failed(result.errorMessage))
        is ApiResult.ApiException -> throw result.exception
    }
}.catch { exception ->
    val className = block.javaClass.name
        .substringAfterLast(".")
        .substringBefore("$")
    val methodName = block.javaClass.enclosingMethod?.name ?: "METHOD_NAME"
    Log.e(className, "$methodName: $exception", exception)
    val error = exception.toString()
    emit(DataState.Failed(error))
}