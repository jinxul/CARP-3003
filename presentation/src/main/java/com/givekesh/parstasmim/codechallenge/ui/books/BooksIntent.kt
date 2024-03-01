package com.givekesh.parstasmim.codechallenge.ui.books

sealed class BooksIntent {
    data object GetBooks : BooksIntent()
    class SearchBook(val searchQuery: String) : BooksIntent()
}