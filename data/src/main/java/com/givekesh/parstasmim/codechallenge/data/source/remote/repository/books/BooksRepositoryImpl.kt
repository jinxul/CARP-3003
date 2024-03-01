package com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books

import com.givekesh.parstasmim.codechallenge.data.entity.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.ResultMessageResponse
import com.givekesh.parstasmim.codechallenge.data.source.remote.api.BooksApi
import retrofit2.HttpException
import javax.inject.Inject

internal class BooksRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
) : BooksRepository {
    override suspend fun getBooks(): List<BookResponse> {
        val response = booksApi.getBooks()
        val body = response.body()
        return when {
            response.isSuccessful && body != null -> body
            else -> throw HttpException(response)
        }
    }

    override suspend fun deleteBook(id: String): ResultMessageResponse {
        val response = booksApi.deleteBook(id)
        val body = response.body()
        return when {
            response.isSuccessful && body != null -> body
            else -> throw HttpException(response)
        }
    }

    override suspend fun addBook(request: BookRequest): ResultMessageResponse {
        val response = booksApi.addBook(request)
        val body = response.body()
        return when {
            response.isSuccessful && body != null -> body
            else -> throw HttpException(response)
        }
    }
}