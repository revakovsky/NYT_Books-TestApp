package com.revakovskyi.books.domain

import com.revakovskyi.core.domain.books.Book
import com.revakovskyi.core.domain.books.Category
import com.revakovskyi.core.domain.books.Store
import com.revakovskyi.core.domain.utils.DataError
import com.revakovskyi.core.domain.utils.EmptyDataResult
import com.revakovskyi.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    fun getBookCategories(forceRefresh: Boolean): Flow<Result<List<Category>, DataError>>
    fun getBooksByCategory(categoryName: String, forceRefresh: Boolean, ): Flow<Result<List<Book>, DataError>>
    fun getStoresWithBook(bookTitle: String): Flow<Result<List<Store>, DataError>>
    suspend fun clearDb(): EmptyDataResult<DataError.Local>

}
