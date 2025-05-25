package com.revakovskyi.books.presentation.books_by_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.revakovskyi.books.domain.BooksRepository
import com.revakovskyi.books.presentation.R
import com.revakovskyi.core.domain.books.Book
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import com.revakovskyi.core.domain.utils.Result
import com.revakovskyi.core.presentation.utils.UiText
import com.revakovskyi.core.presentation.utils.base_viewmodel.BaseViewModel
import com.revakovskyi.core.presentation.utils.text_converters.asUiText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BooksViewModel(
    savedStateHandle: SavedStateHandle,
    private val connectivityObserver: ConnectivityObserver,
    private val booksRepository: BooksRepository,
) : BaseViewModel<BooksState, BooksAction, BooksEvent>(BooksState()) {


    init {
        val categoryName = savedStateHandle["categoryName"] ?: ""

        if (categoryName.isNotEmpty()) {
            updateState { it.copy(categoryName = categoryName, isRefreshing = true) }
            getBooksByCategory(categoryName, forceRefresh = false)
        } else showMissingCategoryNameError()
    }


    override fun onAction(action: BooksAction) {
        when (action) {
            BooksAction.BackToCategories -> sendEvent(BooksEvent.BackToCategories)
            BooksAction.HideStoresDialog -> hideStoresDialog()
            BooksAction.ForceRefreshBooks -> forceRefreshBooks()
            is BooksAction.BuyBook -> buyBook(action.bookId)
            is BooksAction.StoreSelected -> openSelectedStore(action.storeName)
        }
    }

    private fun getBooksByCategory(categoryName: String, forceRefresh: Boolean) {
        booksRepository.getBooksByCategory(categoryName = categoryName, forceRefresh = forceRefresh)
            .onEach { result ->
                when (result) {
                    is Result.Error -> sendEvent(BooksEvent.DataError(result.error.asUiText()))
                    is Result.Success -> handleBooksResult(result.data, forceRefresh)
                }
            }.launchIn(viewModelScope)
    }

    private fun showMissingCategoryNameError() {
        sendEvent(BooksEvent.DataError(UiText.StringResource(R.string.error_missing_category_name)))
    }

    private fun hideStoresDialog() {
        updateState { it.copy(stores = emptyList()) }
    }

    private fun forceRefreshBooks() {
        updateState { it.copy(isRefreshing = true) }
        getBooksByCategory(state.value.categoryName, forceRefresh = true)
    }

    private fun buyBook(bookId: String) {
        // TODO: get stores for the current bookId
    }

    private fun openSelectedStore(storeName: String) {
        // TODO: check the internet and then get a link for the store
    }

    private fun handleBooksResult(books: List<Book>, forceRefresh: Boolean) {
        if (books.isNotEmpty()) {
            updateState { it.copy(isRefreshing = false, books = books) }
            if (forceRefresh) sendEvent(BooksEvent.SuccessfullyRefreshed)
        } else {
            updateState { it.copy(isRefreshing = false) }
            sendEvent(BooksEvent.DataError(UiText.StringResource(R.string.error_no_books_found)))
        }
    }

}
