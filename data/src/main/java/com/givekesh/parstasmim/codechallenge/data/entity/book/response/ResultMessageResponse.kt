package com.givekesh.parstasmim.codechallenge.data.entity.book.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultMessageResponse(
    @Json(name = "status")
    val status: String?,
    @Json(name = "message")
    val message: String,
)
