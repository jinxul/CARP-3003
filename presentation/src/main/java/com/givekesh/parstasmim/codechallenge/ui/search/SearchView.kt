package com.givekesh.parstasmim.codechallenge.ui.search


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.ui.books.BooksIntent
import com.givekesh.parstasmim.codechallenge.ui.books.BooksViewModel

@Composable
fun SearchView(
    viewModel: BooksViewModel,
) {
    var searchQuery by remember { mutableStateOf("") }

    SearchViewContent(
        searchQuery = searchQuery,
        searchResult = viewModel.searchResult,
        onTextChanged = {
            searchQuery = it
            viewModel.processIntent(
                BooksIntent.SearchBook(searchQuery)
            )
        },
    )
}

@Composable
private fun SearchViewContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchResult: List<Book>,
    onTextChanged: (String) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 64.dp,
                start = 12.dp,
                end = 12.dp
            )
        ) {
            items(
                items = searchResult,
                key = { it.id },
            ) { item ->
                SearchItemView(
                    modifier = Modifier.padding(8.dp),
                    book = item,
                )
            }
        }
        SearchInput(
            searchQuery = searchQuery,
            onSearchQueryChange = onTextChanged,
        )
    }
}


@Preview
@Composable
private fun PreviewSearchItemView() {
    SearchViewContent(
        searchQuery = "Something",
        onTextChanged = {},
        searchResult = listOf(
            Book(
                id = "123",
                title = "Book Name",
                author = "Book Author",
            ),
            Book(
                id = "456",
                title = "Book Name 2",
                author = "Book Author 2",
            ),
        )
    )
}
