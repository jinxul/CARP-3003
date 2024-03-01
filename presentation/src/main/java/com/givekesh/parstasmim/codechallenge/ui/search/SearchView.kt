package com.givekesh.parstasmim.codechallenge.ui.search


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book

@Composable
fun SearchView() {
    var isLoading by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val searchResult = remember { mutableStateListOf<Book>() }

    SearchViewContent(
        searchQuery = searchQuery,
        searchResult = searchResult,
        onTextChanged = { searchQuery = it },
        isLoading = isLoading,
    )
}

@Composable
private fun SearchViewContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchResult: List<Book>,
    onTextChanged: (String) -> Unit,
    isLoading: Boolean,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp),
            )
        } else {
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
        isLoading = false,
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
