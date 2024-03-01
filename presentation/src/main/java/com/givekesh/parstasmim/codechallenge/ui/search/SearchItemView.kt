package com.givekesh.parstasmim.codechallenge.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

            )
        Text(
            text = book.title,
            style = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
            ),
        )
    }
}


@Preview
@Composable
private fun PreviewSearchItemView() {
    SearchItemView(
        book = Book(
            id = "123",
            title = "Book Name",
            author = "Book Author",
        ),
    )
}