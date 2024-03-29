package com.givekesh.parstasmim.codechallenge.data.source.remote.api

import com.givekesh.parstasmim.codechallenge.data.entity.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.ResultMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

internal interface BooksApi {
    @GET("/books")
    suspend fun getBooks(): Response<List<BookResponse>>

    @DELETE("/books/{id}")
    suspend fun deleteBook(@Path("id") id: String): Response<ResultMessageResponse>

    @POST("/books")
    suspend fun addBook(@Body request: BookRequest): Response<ResultMessageResponse>

    @PATCH("/books/{id}")
    suspend fun editBook(
        @Path("id") id: String,
        @Body request: BookRequest
    ): Response<ResultMessageResponse>
}