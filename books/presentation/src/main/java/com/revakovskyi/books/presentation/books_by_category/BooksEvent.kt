package com.revakovskyi.books.presentation.books_by_category

import com.revakovskyi.core.presentation.utils.UiText

sealed interface BooksEvent {

    data object BackToCategories : BooksEvent
    data object SuccessfullyRefreshed : BooksEvent
    data class OpenStore(val url: String) : BooksEvent
    data class DataError(val message: UiText) : BooksEvent

}
