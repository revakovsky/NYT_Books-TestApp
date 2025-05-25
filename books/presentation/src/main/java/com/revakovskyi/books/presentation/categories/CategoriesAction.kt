package com.revakovskyi.books.presentation.categories

sealed interface CategoriesAction {

    data object ForceRefreshCategories : CategoriesAction
    data class OpenBooksByCategory(val categoryName: String) : CategoriesAction
    data object SignOut : CategoriesAction

}
