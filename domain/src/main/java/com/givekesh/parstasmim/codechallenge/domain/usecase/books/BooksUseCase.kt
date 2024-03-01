package com.givekesh.parstasmim.codechallenge.domain.usecase.books

import com.givekesh.parstasmim.codechallenge.domain.model.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.domain.util.DataState
import kotlinx.coroutines.flow.Flow

interface BooksUseCase {
    fun getBooks(): Flow<DataState<List<Book>>>
    fun deleteBook(bookId: String): Flow<DataState<Boolean>>
    fun addBook(request: BookRequest): Flow<DataState<Boolean>>
}