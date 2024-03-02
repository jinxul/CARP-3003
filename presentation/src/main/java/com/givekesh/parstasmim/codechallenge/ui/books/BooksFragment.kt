package com.givekesh.parstasmim.codechallenge.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.givekesh.parstasmim.codechallenge.R
import com.givekesh.parstasmim.codechallenge.databinding.DialogBookBinding
import com.givekesh.parstasmim.codechallenge.databinding.FragmentBooksBinding
import com.givekesh.parstasmim.codechallenge.domain.model.book.request.BookRequest
import com.givekesh.parstasmim.codechallenge.domain.model.book.response.Book
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
            onEditClick = { book -> showBookDialog(isEdit = true, book = book) },
        )
        binding.apply {
            booksList.adapter = booksAdapter
            fab.setOnClickListener {
                showBookDialog(isEdit = false)
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

    private fun showBookDialog(isEdit: Boolean, book: Book? = null) {
        val dialogTitle = when (isEdit) {
            true -> R.string.edit_book
            false -> R.string.add_book
        }
        val binding = DialogBookBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(dialogTitle)
            .show()

        binding.apply {
            if (book != null) {
                bookTitleInput.setText(book.title)
                bookAuthorInput.setText(book.author)
                bookGenreInput.setText(book.genre)
                bookYearPublishedInput.setText(book.yearPublished.toString())
                checkedOutCheckBox.isChecked = book.isCheckedOut
                checkedOutGroup.isVisible = true
                positiveButton.setText(R.string.edit_book)
            } else {
                checkedOutGroup.isVisible = false
                positiveButton.setText(R.string.add_book)
            }

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

                val isCheckedOut = when (isEdit) {
                    true -> checkedOutCheckBox.isChecked
                    false -> null
                }

                val request = BookRequest(
                    title = bookTitleInput.text.toString(),
                    author = bookAuthorInput.text.toString(),
                    genre = bookGenreInput.text.toString(),
                    yearPublished = yearPublished,
                    isCheckedOut = isCheckedOut,
                )
                viewModel.lastNetworkCall = when (isEdit) {
                    true -> BooksIntent.EditBook(book!!.id, request)
                    false -> BooksIntent.AddBook(request)
                }.also { viewModel.processIntent(it) }

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
                    DataState.Loading -> binding.loading.isVisible = true
                    is DataState.Successful -> {
                        binding.loading.isVisible = false
                        booksAdapter.updateItems(dataState.data)
                    }

                    is DataState.Failed -> showError(dataState.error)
                }
            }
        }
    }

    private fun subscribeObserverForResultMessage() {
        resultMessageJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resultMessageDataState.collectLatest { dataState ->
                when (dataState) {
                    DataState.Idle -> Unit
                    DataState.Loading -> binding.loading.isVisible = true
                    is DataState.Successful -> getBooks()
                    is DataState.Failed -> showError(dataState.error)
                }
            }
        }
    }


    private fun showError(errorMessage: String) {
        binding.loading.isVisible = false
        Snackbar.make(
            requireContext(),
            binding.root,
            errorMessage,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.retry)) {
                viewModel.lastNetworkCall?.also { viewModel.processIntent(it) }
            }
            .setAnchorView(R.id.fab)
            .show()
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