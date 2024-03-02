package com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books

import com.givekesh.parstasmim.codechallenge.data.entity.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.ResultMessageResponse
import com.givekesh.parstasmim.codechallenge.data.util.ApiResult

interface BooksRepository {
    suspend fun getBooks(): ApiResult<List<BookResponse>>
    suspend fun deleteBook(id: String): ApiResult<ResultMessageResponse>
    suspend fun addBook(request: BookRequest): ApiResult<ResultMessageResponse>
    suspend fun editBook(bookId: String, request: BookRequest): ApiResult<ResultMessageResponse>
}