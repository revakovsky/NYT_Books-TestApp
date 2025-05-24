package com.revakovskyi.books.data.mappers

import com.revakovskyi.core.database.entities.BookEntity
import com.revakovskyi.core.database.entities.CategoryEntity
import com.revakovskyi.core.database.entities.StoreEntity
import com.revakovskyi.core.domain.books.Book
import com.revakovskyi.core.domain.books.Category
import com.revakovskyi.core.domain.books.Store

fun CategoryEntity.toCategory(): Category {
    return Category(
        name = this.name,
        updatingFrequency = this.updatingFrequency,
        publishedDate = this.publishedDate
    )
}


fun BookEntity.toBook(): Book {
    return Book(
        id = this.id,
        categoryName = this.categoryName,
        title = this.title,
        author = this.author,
        description = this.description,
        publisher = this.publisher,
        image = this.image.orEmpty(),
        rank = this.rank,
    )
}


fun StoreEntity.toStore(): Store {
    return Store(
        name = name,
        bookTitle = this.bookTitle,
        url = this.url
    )
}
