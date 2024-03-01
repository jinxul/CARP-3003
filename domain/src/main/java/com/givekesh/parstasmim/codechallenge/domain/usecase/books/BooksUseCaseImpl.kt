package com.givekesh.parstasmim.codechallenge.domain.usecase.books

import com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books.BooksRepository
import com.givekesh.parstasmim.codechallenge.domain.mapper.books.BookRequestMapper
import com.givekesh.parstasmim.codechallenge.domain.mapper.books.BooksListMapper
import com.givekesh.parstasmim.codechallenge.domain.model.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.domain.util.DataState
import com.givekesh.parstasmim.codechallenge.domain.util.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class BooksUseCaseImpl @Inject constructor(
    private val booksRepository: BooksRepository,
    private val booksListMapper: BooksListMapper,
    private val bookRequestMapper: BookRequestMapper,
) : BooksUseCase {
    override fun getBooks(): Flow<DataState<List<Book>>> = safeFlow(
        apiCall = { booksRepository.getBooks() },
        block = { apiResponse ->
            booksListMapper.toList(apiResponse)
                .also { emit(DataState.Successful(it)) }
        }
    )

    override fun deleteBook(bookId: String): Flow<DataState<Boolean>> = safeFlow(
        apiCall = { booksRepository.deleteBook(bookId) },
        block = { apiResponse ->
            when {
                apiResponse.status.equals("error", false) -> DataState.Failed(apiResponse.message)
                else -> DataState.Successful(true)
            }.also { emit(it) }
        }
    )

    override fun addBook(request: BookRequest): Flow<DataState<Boolean>> = safeFlow(
        apiCall = {
            bookRequestMapper.toObject(request)
                .let { booksRepository.addBook(it) }
        },
        block = { apiResponse ->
            when {
                apiResponse.status.equals("error", false) -> DataState.Failed(apiResponse.message)
                else -> DataState.Successful(true)
            }.also { emit(it) }
        }
    )
}