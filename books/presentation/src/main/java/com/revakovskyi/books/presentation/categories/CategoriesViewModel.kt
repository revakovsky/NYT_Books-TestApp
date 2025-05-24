package com.revakovskyi.books.presentation.categories

import androidx.lifecycle.viewModelScope
import com.revakovskyi.books.domain.BooksRepository
import com.revakovskyi.core.domain.utils.Result
import com.revakovskyi.core.presentation.utils.base_viewmodel.BaseViewModel
import com.revakovskyi.core.presentation.utils.text_converters.asUiText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoriesViewModel(
    private val booksRepository: BooksRepository,
) : BaseViewModel<CategoriesState, CategoriesAction, CategoriesEvent>(CategoriesState()) {


    init {
        getBookCategories(forceRefresh = false)
    }


    override fun onAction(action: CategoriesAction) {
        when (action) {
            CategoriesAction.ForceUpdateCategories -> getBookCategories(forceRefresh = true)
            is CategoriesAction.OpenBooksByCategory -> openBooksByCategory(action.categoryName)
            CategoriesAction.SignOut -> signOut()
        }
    }

    private fun getBookCategories(forceRefresh: Boolean) {
        booksRepository.getBookCategories(forceRefresh)
            .onEach { result ->
                when (result) {
                    is Result.Error -> sendEvent(CategoriesEvent.DataError(result.error.asUiText()))
                    is Result.Success -> updateState { it.copy(categories = result.data) }
                }
                updateState { it.copy(isLoading = false) }
            }.launchIn(viewModelScope)
    }

    private fun openBooksByCategory(categoryName: String) {
        sendEvent(CategoriesEvent.OpenBooksByCategory(categoryName))
    }

    private fun signOut() {
        sendEvent(CategoriesEvent.SignOut)
    }

}
