package com.revakovskyi.books.presentation.books_by_category

import com.revakovskyi.core.domain.books.Book
import com.revakovskyi.core.domain.books.Store

data class BooksState(
    val isRefreshing: Boolean = false,
    val showStoresDialog: Boolean = false,
    val categoryName: String = "",
    val books: List<Book> = emptyList(),
    val stores: List<Store> = emptyList(),
)
