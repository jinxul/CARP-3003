package com.givekesh.parstasmim.codechallenge.ui.books

import com.givekesh.parstasmim.codechallenge.domain.model.book.request.BookRequest

sealed class BooksIntent {
    data object GetBooks : BooksIntent()
    class SearchBook(val searchQuery: String) : BooksIntent()
    class DeleteBook(val bookId: String) : BooksIntent()
    class AddBook(val request: BookRequest) : BooksIntent()
}