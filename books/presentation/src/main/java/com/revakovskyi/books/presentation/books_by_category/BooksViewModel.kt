package com.revakovskyi.books.presentation.books_by_category

import androidx.lifecycle.SavedStateHandle
import com.revakovskyi.books.domain.BooksRepository
import com.revakovskyi.books.presentation.R
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import com.revakovskyi.core.presentation.utils.UiText
import com.revakovskyi.core.presentation.utils.base_viewmodel.BaseViewModel

class BooksViewModel(
    savedStateHandle: SavedStateHandle,
    private val connectivityObserver: ConnectivityObserver,
    private val booksRepository: BooksRepository,
) : BaseViewModel<BooksState, BooksAction, BooksEvent>(BooksState()) {


    init {
        val categoryName = savedStateHandle["categoryName"] ?: ""

        if (categoryName.isNotEmpty()) updateState { it.copy(categoryName = categoryName) }
        else showMissingCategoryNameError()
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

    private fun hideStoresDialog() {
        updateState { it.copy(stores = emptyList()) }
    }

    private fun forceRefreshBooks() {
        // TODO: update books list
    }

    private fun buyBook(bookId: String) {
        // TODO: get stores for the current bookId
    }

    private fun openSelectedStore(storeName: String) {
        // TODO: check the internet and then get a link for the store
    }

    private fun showMissingCategoryNameError() {
        sendEvent(
            BooksEvent.DataError(
                UiText.StringResource(R.string.error_missing_category_name)
            )
        )
    }

}
