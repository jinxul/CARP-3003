package com.givekesh.parstasmim.codechallenge.domain.model.book.request

data class BookRequest(
    val title: String,
    val author: String,
    val genre: String,
    val yearPublished: Int
)
