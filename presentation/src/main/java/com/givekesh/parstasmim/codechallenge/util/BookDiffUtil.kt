package com.givekesh.parstasmim.codechallenge.util

import androidx.recyclerview.widget.DiffUtil
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book

object BookDiffUtil : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(
        oldItem: Book,
        newItem: Book
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Book,
        newItem: Book
    ): Boolean = oldItem == newItem
}