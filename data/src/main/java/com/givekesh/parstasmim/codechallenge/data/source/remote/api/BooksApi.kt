package com.givekesh.parstasmim.codechallenge.data.source.remote.api

import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import retrofit2.http.GET
import retrofit2.Response

internal interface BooksApi {
    @GET("/books")
    suspend fun getBooks(): Response<List<BookResponse>>
}