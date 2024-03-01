package com.givekesh.parstasmim.codechallenge.data.entity.book.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookRequest(
    @Json(name = "author")
    val author: String,
    @Json(name = "genre")
    val genre: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "yearPublished")
    val yearPublished: Int
)