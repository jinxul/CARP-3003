package com.givekesh.parstasmim.codechallenge.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.domain.usecase.books.BooksUseCase
import com.givekesh.parstasmim.codechallenge.domain.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val booksUseCase: BooksUseCase,
) : ViewModel() {
    private val intentChannel = Channel<BooksIntent>()

    private val _dataState = MutableStateFlow<DataState<List<Book>>>(DataState.Idle)
    val dataState = _dataState.asStateFlow()

    private val _resultMessageDataState = MutableStateFlow<DataState<Boolean>>(DataState.Idle)
    val resultMessageDataState = _resultMessageDataState.asStateFlow()

    val searchResult = mutableListOf<Book>()
    var lastNetworkCall: BooksIntent? = null

    init {
        observeChannelIntent()
    }

    private fun observeChannelIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collectLatest { intent ->
                when (intent) {
                    BooksIntent.GetBooks -> booksUseCase.getBooks()
                        .onEach { _dataState.value = it }
                        .launchIn(viewModelScope)

                    is BooksIntent.SearchBook -> searchBook(intent.searchQuery)
                    is BooksIntent.DeleteBook -> booksUseCase.deleteBook(intent.bookId)
                        .onEach { _resultMessageDataState.value = it }
                        .launchIn(viewModelScope)

                    is BooksIntent.AddBook -> booksUseCase.addBook(intent.request)
                        .onEach { _resultMessageDataState.value = it }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    private fun searchBook(searchQuery: String) {
        val booksDataState = dataState.value
        if (booksDataState is DataState.Successful) {
            searchResult.clear()
            booksDataState.data.filter { it.title.contains(searchQuery) }
                .also { searchResult.addAll(it) }
        }
    }

    fun processIntent(intent: BooksIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }
}