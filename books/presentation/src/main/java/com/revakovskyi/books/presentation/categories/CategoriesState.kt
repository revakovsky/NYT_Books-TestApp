package com.revakovskyi.books.presentation.categories

import com.revakovskyi.core.domain.books.Category

data class CategoriesState(
    val isRefreshing: Boolean = false,
    val categories: List<Category> = emptyList(),
)
