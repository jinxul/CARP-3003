package com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books

import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse

interface BooksRepository {
    suspend fun getBooks(): List<BookResponse>
}