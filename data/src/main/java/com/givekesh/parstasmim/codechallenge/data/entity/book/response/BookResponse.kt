package com.givekesh.parstasmim.codechallenge.data.entity.book.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookResponse(
    @Json(name = "author")
    val author: String,
    @Json(name = "checkedOut")
    val checkedOut: Boolean,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "genre")
    val genre: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "yearPublished")
    val yearPublished: Int
)