package com.revakovskyi.core.data.books.mappers

import com.revakovskyi.core.database.entities.BookEntity
import com.revakovskyi.core.database.entities.CategoryEntity
import com.revakovskyi.core.database.entities.StoreEntity
import com.revakovskyi.core.network.dto.BooksOverviewDto

fun BooksOverviewDto.toCategoryEntities(): List<CategoryEntity> {
    return this.results.categories.map { categoryDto ->
        CategoryEntity(
            name = categoryDto.name,
            updatingFrequency = categoryDto.updatingFrequency,
            publishedDate = this.results.publishedDate
        )
    }
}


fun BooksOverviewDto.toBookEntities(): List<BookEntity> {
    return this.results.categories.flatMap { categoryDto ->
        categoryDto.books.map { bookDto ->
            BookEntity(
                categoryName = categoryDto.name,
                title = bookDto.title,
                author = bookDto.author,
                description = bookDto.description,
                publisher = bookDto.publisher,
                image = bookDto.image,
                rank = bookDto.rank,
            )
        }
    }
}


fun BooksOverviewDto.toStoreEntities(): List<StoreEntity> {
    return this.results.categories.flatMap { categoryDto ->
        categoryDto.books.flatMap { bookDto ->
            bookDto.stores.map { storeDto ->
                StoreEntity(
                    name = storeDto.name,
                    bookTitle = bookDto.title,
                    url = storeDto.url
                )
            }
        }
    }
}
