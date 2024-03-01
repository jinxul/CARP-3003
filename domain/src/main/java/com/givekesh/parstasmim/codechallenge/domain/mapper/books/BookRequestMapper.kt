package com.givekesh.parstasmim.codechallenge.domain.mapper.books

import com.givekesh.parstasmim.codechallenge.domain.model.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.domain.util.ObjectToObjectMapper
import javax.inject.Inject
import com.givekesh.parstasmim.codechallenge.data.entity.book.request.BookRequest as BookRequestEntity

internal class BookRequestMapper @Inject constructor(
) : ObjectToObjectMapper<BookRequest, BookRequestEntity> {
    override fun toObject(from: BookRequest) = BookRequestEntity(
        title = from.title,
        author = from.author,
        genre = from.genre,
        yearPublished = from.yearPublished,
    )
}