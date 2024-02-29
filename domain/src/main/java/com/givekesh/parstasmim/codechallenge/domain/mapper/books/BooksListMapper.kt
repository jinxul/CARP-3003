package com.givekesh.parstasmim.codechallenge.domain.mapper.books

import com.givekesh.parstasmim.codechallenge.data.entity.book.response.BookResponse
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.domain.util.ListToListMapper
import javax.inject.Inject

internal class BooksListMapper @Inject constructor(
    private val bookMapper: BookMapper,
) : ListToListMapper<BookResponse, Book> {
    override fun toList(from: List<BookResponse>): List<Book> {
        return from.map { bookMapper.toObject(it) }
    }
}