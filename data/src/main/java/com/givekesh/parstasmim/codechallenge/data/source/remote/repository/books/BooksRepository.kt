package com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books

import com.givekesh.parstasmim.codechallenge.data.entity.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import com.givekesh.parstasmim.codechallenge.data.entity.book.response.ResultMessageResponse

interface BooksRepository {
    suspend fun getBooks(): List<BookResponse>
    suspend fun deleteBook(id: String): ResultMessageResponse
    suspend fun addBook(request: BookRequest): ResultMessageResponse
}