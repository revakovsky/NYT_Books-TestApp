package com.revakovskyi.books.presentation.categories

import androidx.lifecycle.viewModelScope
import com.revakovskyi.books.domain.BooksRepository
import com.revakovskyi.core.domain.utils.Result
import com.revakovskyi.core.presentation.utils.base_viewmodel.BaseViewModel
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
            CategoriesAction.SignOut -> signOut()
        }
    }

    private fun getBookCategories(forceRefresh: Boolean) {
        booksRepository.getBookCategories(forceRefresh)
            .onEach { result ->
                when (result) {
                    is Result.Error -> {
                        // TODO
                    }

                    is Result.Success -> {
                        // TODO
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun signOut() {
        sendEvent(CategoriesEvent.SignOut)
    }

}
