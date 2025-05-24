package com.revakovskyi.core.domain.books

data class Book(
    val id: String,
    val categoryName: String,
    val title: String,
    val author: String,
    val description: String,
    val publisher: String,
    val image: String,
    val rank: Int,
)
