package com.revakovskyi.books.presentation.categories

data class CategoriesState(
    val isLoading: Boolean = true,
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)
