package com.givekesh.parstasmim.codechallenge.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book


@Composable
fun SearchItemView(
    modifier: Modifier = Modifier,
    book: Book,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = book.title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            text = book.title,
            style = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
            ),
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

private class SearchItemPreviewParameterProvider : PreviewParameterProvider<Book> {
    override val values: Sequence<Book>
        get() = sequenceOf(
            Book(
                id = "123",
                title = "Book Name",
                author = "Book Author",
                genre = "Book Genre",
                yearPublished = 2024,
            )
        )
}

@Preview
@Composable
private fun PreviewSearchItemView(
    @PreviewParameter(SearchItemPreviewParameterProvider::class, limit = 1) book: Book,
) {
    SearchItemView(
        book = book,
    )
}