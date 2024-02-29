package com.givekesh.parstasmim.codechallenge.domain.util

internal interface ObjectToObjectMapper<T, R> {
    fun toObject(from: T): R
}

internal interface ListToListMapper<T, R> {
    fun toList(from: List<T>): List<R>
}

