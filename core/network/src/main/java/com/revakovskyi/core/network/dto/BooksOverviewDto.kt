package com.revakovskyi.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksOverviewDto(
    @SerialName("results") val results: BooksOverviewResults,
)


@Serializable
data class BooksOverviewResults(
    @SerialName("published_date") val publishedDate: String,
    @SerialName("lists") val categories: List<CategoryDto>,
)


@Serializable
data class CategoryDto(
    @SerialName("display_name") val name: String,
    @SerialName("updated") val updatingFrequency: String,
    @SerialName("books") val books: List<BookDto>,
)


@Serializable
data class BookDto(
    @SerialName("title") val title: String,
    @SerialName("author") val author: String,
    @SerialName("description") val description: String,
    @SerialName("publisher") val publisher: String,
    @SerialName("book_image") val image: String,
    @SerialName("rank") val rank: Int,
    @SerialName("buy_links") val stores: List<StoreDto>,
)


@Serializable
data class StoreDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String,
)
