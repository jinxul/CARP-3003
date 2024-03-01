package com.givekesh.parstasmim.codechallenge.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.givekesh.parstasmim.codechallenge.BR
import com.givekesh.parstasmim.codechallenge.R
import com.givekesh.parstasmim.codechallenge.databinding.FragmentBooksBinding
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
import com.givekesh.parstasmim.codechallenge.domain.util.DataState
import com.givekesh.parstasmim.codechallenge.util.BookDiffUtil
import com.givekesh.parstasmim.codechallenge.util.GenericAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BooksFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var _booksAdapter: GenericAdapter<Book>? = null
    private val booksAdapter: GenericAdapter<Book> get() = _booksAdapter!!


    private var booksJob: Job? = null
    private val viewModel: BooksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        subscribeObserver()
        getBooks()
    }

    private fun setupViews() {
        _booksAdapter = GenericAdapter(
            layoutResId = R.layout.item_book,
            bindingVariableId = BR.book,
            diffCallBack = BookDiffUtil
        )
        binding.booksList.adapter = booksAdapter
    }

    private fun subscribeObserver() {
        booksJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataState.collectLatest { dataState ->
                when (dataState) {
                    DataState.Idle -> Unit
                    DataState.Loading -> Unit
                    is DataState.Successful -> booksAdapter.updateItems(dataState.data)
                    is DataState.Failed -> Unit
                }
            }
        }
    }


    private fun getBooks() {
        viewModel.processIntent(
            BooksIntent.GetBooks
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        booksJob?.cancel()
        booksJob = null
        _booksAdapter = null
        _binding = null
    }
}