package com.givekesh.parstasmim.codechallenge.domain.model.book.response

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val genre: String,
    val yearPublished: Int,
    val isCheckedOut: Boolean,
)
