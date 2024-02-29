package com.givekesh.parstasmim.codechallenge.domain.util

sealed class DataState<out R> {
    data object Idle : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    class Successful<T>(val data: T) : DataState<T>()
    class Failed(val error: String) : DataState<Nothing>()
}