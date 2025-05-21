package com.revakovskyi.books.presentation.categories

sealed interface CategoriesAction {

    data object SignOut : CategoriesAction

}
