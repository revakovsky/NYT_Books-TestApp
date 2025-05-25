package com.revakovskyi.books.presentation.books_by_category

sealed interface BooksAction {

    data object BackToCategories : BooksAction
    data object HideStoresDialog : BooksAction
    data object ForceRefreshBooks : BooksAction
    data class BuyBook(val bookId: String) : BooksAction
    data class StoreSelected(val storeUrl: String) : BooksAction

}
