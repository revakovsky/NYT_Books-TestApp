package com.revakovskyi.books.presentation.categories

import com.revakovskyi.core.domain.books.Category

data class CategoriesState(
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList(),
)
