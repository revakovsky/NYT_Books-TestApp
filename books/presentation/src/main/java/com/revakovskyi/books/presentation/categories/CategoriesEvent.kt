package com.revakovskyi.books.presentation.categories

sealed interface CategoriesEvent {

    data object SignOut : CategoriesEvent

}
