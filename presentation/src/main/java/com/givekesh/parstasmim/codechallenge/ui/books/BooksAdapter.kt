package com.givekesh.parstasmim.codechallenge.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.givekesh.parstasmim.codechallenge.BR
import com.givekesh.parstasmim.codechallenge.databinding.ItemBookBinding
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.util.BookDiffUtil

class BooksAdapter(
    private val onDeleteClick: (Book) -> Unit,
) : RecyclerView.Adapter<BooksViewHolder>() {
    private val differ = AsyncListDiffer(this, BookDiffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater)
        return BooksViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, BR.book)
    }

    override fun getItemCount(): Int = differ.currentList.size

    private fun getItem(position: Int): Book = differ.currentList[position]

    fun updateItems(items: List<Book>) = differ.submitList(items)
}

class BooksViewHolder(
    private val binding: ItemBookBinding,
    private val onDeleteClick: (Book) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Book, bindingVariableId: Int) {
        binding.apply {
            setVariable(bindingVariableId, item)
            delete.setOnClickListener { onDeleteClick(item) }
        }
    }
}