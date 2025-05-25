package com.revakovskyi.books.presentation.categories

import com.revakovskyi.core.presentation.utils.UiText

sealed interface CategoriesEvent {

    data object SuccessfullyRefreshed : CategoriesEvent
    data class DataError(val message: UiText) : CategoriesEvent
    data object SignOut : CategoriesEvent
    data class OpenBooksByCategory(val categoryName: String) : CategoriesEvent

}
