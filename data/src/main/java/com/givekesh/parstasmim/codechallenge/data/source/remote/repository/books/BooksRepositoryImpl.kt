package com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books

import com.givekesh.parstasmim.codechallenge.data.entity.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.ResultMessageResponse
import com.givekesh.parstasmim.codechallenge.data.source.remote.api.BooksApi
import com.givekesh.parstasmim.codechallenge.data.util.ApiResult
import com.givekesh.parstasmim.codechallenge.data.util.safeApiCall
import javax.inject.Inject

internal class BooksRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
) : BooksRepository {
    override suspend fun getBooks(): ApiResult<List<BookResponse>> =
        safeApiCall { booksApi.getBooks() }

    override suspend fun deleteBook(
        id: String
    ): ApiResult<ResultMessageResponse> = safeApiCall { booksApi.deleteBook(id) }

    override suspend fun addBook(
        request: BookRequest
    ): ApiResult<ResultMessageResponse> = safeApiCall { booksApi.addBook(request) }

    override suspend fun editBook(
        bookId: String,
        request: BookRequest
    ): ApiResult<ResultMessageResponse> = safeApiCall { booksApi.editBook(bookId, request) }
}