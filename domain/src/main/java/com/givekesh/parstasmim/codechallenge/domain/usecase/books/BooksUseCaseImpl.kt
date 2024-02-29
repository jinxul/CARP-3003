package com.givekesh.parstasmim.codechallenge.domain.usecase.books

import com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books.BooksRepository
import com.givekesh.parstasmim.codechallenge.domain.mapper.books.BooksListMapper
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.domain.util.DataState
import com.givekesh.parstasmim.codechallenge.domain.util.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class BooksUseCaseImpl @Inject constructor(
    private val booksRepository: BooksRepository,
    private val booksListMapper: BooksListMapper,
) : BooksUseCase {
    override fun getBooks(): Flow<DataState<List<Book>>> = safeFlow(
        apiCall = { booksRepository.getBooks() },
        block = { apiResponse ->
            booksListMapper.toList(apiResponse)
                .also { emit(DataState.Successful(it)) }
        }
    )
}