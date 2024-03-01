package com.givekesh.parstasmim.codechallenge.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.givekesh.parstasmim.codechallenge.R
import com.givekesh.parstasmim.codechallenge.databinding.FragmentBooksBinding
import com.givekesh.parstasmim.codechallenge.domain.util.DataState
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

    private var _booksAdapter: BooksAdapter? = null
    private val booksAdapter: BooksAdapter get() = _booksAdapter!!

    private var booksJob: Job? = null
    private var resultMessageJob: Job? = null
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
        subscribeObservers()
        getBooks()
    }

    private fun setupViews() {
        _booksAdapter = BooksAdapter(
            onDeleteClick = { book -> deleteBook(book.id) },
        )
        binding.booksList.adapter = booksAdapter
    }

    private fun deleteBook(bookId: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_title)
            .setMessage(R.string.are_you_sure)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.processIntent(
                    BooksIntent.DeleteBook(bookId)
                )
                dialog.dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun subscribeObservers() {
        subscribeObserverForBooks()
        subscribeObserverForResultMessage()
    }

    private fun subscribeObserverForBooks() {
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

    private fun subscribeObserverForResultMessage() {
        resultMessageJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resultMessageDataState.collectLatest { dataState ->
                when (dataState) {
                    DataState.Idle -> Unit
                    DataState.Loading -> Unit
                    is DataState.Successful -> getBooks()
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
        resultMessageJob?.cancel()
        resultMessageJob = null
        booksJob?.cancel()
        booksJob = null
        _booksAdapter = null
        _binding = null
    }
}