package com.givekesh.parstasmim.codechallenge.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.givekesh.parstasmim.codechallenge.R

@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = White,
            focusedIndicatorColor = Gray,
            focusedTextColor = Black,

            unfocusedContainerColor = White,
            unfocusedIndicatorColor = Gray,
            unfocusedTextColor = Black,

            cursorColor = Gray,
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_normal),
                contentDescription = "discover icon",
                tint = Black,
            )
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(400),
        ),
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Gray,
                ),
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchInput() {
    SearchInput(
        searchQuery = "",
        onSearchQueryChange = {},
    )
}