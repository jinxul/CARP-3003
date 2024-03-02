package com.givekesh.parstasmim.codechallenge.domain.mapper.books

import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.domain.util.ObjectToObjectMapper
import javax.inject.Inject

internal class BookMapper @Inject constructor(
) : ObjectToObjectMapper<BookResponse, Book> {
    override fun toObject(from: BookResponse) = Book(
        id = from.id,
        title = from.title,
        author = from.author,
        genre = from.genre,
        yearPublished = from.yearPublished,
        isCheckedOut = from.checkedOut,
    )
}