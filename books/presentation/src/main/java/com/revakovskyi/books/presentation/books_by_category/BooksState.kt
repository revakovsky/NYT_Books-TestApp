package com.revakovskyi.books.presentation.books_by_category

import com.revakovskyi.core.domain.books.Book

data class BooksState(
    val isRefreshing: Boolean = false,
    val categoryName: String = "",
    val books: List<Book> = emptyList(),
    val stores: List<Book> = emptyList(),
)
