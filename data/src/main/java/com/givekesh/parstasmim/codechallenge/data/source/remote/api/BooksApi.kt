package com.givekesh.parstasmim.codechallenge.data.source.remote.api

import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.ResultMessageResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

internal interface BooksApi {
    @GET("/books")
    suspend fun getBooks(): Response<List<BookResponse>>

    @DELETE("/books/{id}")
    suspend fun deleteBook(@Path("id") id: String): Response<ResultMessageResponse>
}