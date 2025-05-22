package com.revakovskyi.books.presentation.categories

import com.revakovskyi.core.presentation.utils.base_viewmodel.BaseViewModel

class CategoriesViewModel : BaseViewModel<CategoriesState, CategoriesAction, CategoriesEvent>(
    CategoriesState()
) {

    override fun onAction(action: CategoriesAction) {
        when (action) {
            CategoriesAction.SignOut -> signOut()
        }
    }

    private fun signOut() {
        sendEvent(CategoriesEvent.SignOut)
    }

}
