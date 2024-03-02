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
import com.givekesh.parstasmim.codechallenge.databinding.DialogBookBinding
import com.givekesh.parstasmim.codechallenge.databinding.FragmentBooksBinding
import com.givekesh.parstasmim.codechallenge.domain.model.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.domain.util.DataState
import com.google.android.material.snackbar.Snackbar
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
        binding.apply {
            booksList.adapter = booksAdapter
            fab.setOnClickListener {
                showAddBookDialog()
            }
        }
    }

    private fun deleteBook(bookId: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_title)
            .setMessage(R.string.are_you_sure)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.lastNetworkCall = BooksIntent.DeleteBook(bookId)
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

    private fun showAddBookDialog() {
        val binding = DialogBookBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(R.string.add_book)
            .show()

        binding.apply {
            positiveButton.setOnClickListener {
                val title = bookTitleInput.text.toString()
                val author = bookAuthorInput.text.toString()
                val genre = bookGenreInput.text.toString()
                val year = bookYearPublishedInput.text.toString()

                if (title.isBlank()) {
                    bookTitleInputLayout.error = getString(R.string.title_is_required)
                    return@setOnClickListener
                } else {
                    bookTitleInputLayout.error = null
                }

                if (author.isBlank()) {
                    bookAuthorInputLayout.error = getString(R.string.author_is_required)
                    return@setOnClickListener
                } else {
                    bookAuthorInputLayout.error = null
                }

                if (genre.isBlank()) {
                    bookGenreInputLayout.error = getString(R.string.genre_is_required)
                    return@setOnClickListener
                } else {
                    bookGenreInputLayout.error = null
                }

                if (year.isBlank()) {
                    bookYearPublishedInputLayout.error =
                        getString(R.string.published_year_is_required)
                    return@setOnClickListener
                } else {
                    bookYearPublishedInputLayout.error = null
                }

                val yearPublished = bookYearPublishedInput.text.toString()
                    .filter { it.isDigit() }
                    .toInt()
                if (yearPublished !in 1900..2024) {
                    bookYearPublishedInputLayout.error =
                        getString(R.string.published_year_error_message)
                    return@setOnClickListener
                } else {
                    bookYearPublishedInputLayout.error = null
                }

                val request = BookRequest(
                    title = bookTitleInput.text.toString(),
                    author = bookAuthorInput.text.toString(),
                    genre = bookGenreInput.text.toString(),
                    yearPublished = yearPublished,
                )
                viewModel.lastNetworkCall = BooksIntent.AddBook(request)
                viewModel.processIntent(
                    BooksIntent.AddBook(request)
                )
                dialog.dismiss()
            }

            negativeButton.setOnClickListener {
                dialog.dismiss()
            }
        }
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
                    is DataState.Failed ->
                        Snackbar.make(binding.root, dataState.error, Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.retry)) {
                                viewModel.lastNetworkCall?.also { viewModel.processIntent(it) }
                            }
                            .setAnchorView(R.id.fab)
                            .show()
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
                    is DataState.Failed -> Snackbar.make(
                        binding.root,
                        dataState.error,
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(getString(R.string.retry)) {
                            viewModel.lastNetworkCall?.also { viewModel.processIntent(it) }
                        }
                        .setAnchorView(R.id.fab)
                        .show()
                }
            }
        }
    }


    private fun getBooks() {
        viewModel.lastNetworkCall = BooksIntent.GetBooks
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