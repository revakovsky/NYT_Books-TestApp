package com.revakovskyi.core.database

import com.revakovskyi.core.database.dao.BooksDao
import com.revakovskyi.core.database.entities.BookEntity
import com.revakovskyi.core.database.entities.CategoryEntity
import com.revakovskyi.core.database.entities.StoreEntity
import com.revakovskyi.core.database.tools.safeDbCall
import com.revakovskyi.core.database.tools.safeDbFlowCall
import com.revakovskyi.core.domain.utils.DataError
import com.revakovskyi.core.domain.utils.DispatcherProvider
import com.revakovskyi.core.domain.utils.EmptyDataResult
import com.revakovskyi.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface BooksDbClient {
    suspend fun insertBooksOverview(
        categories: List<CategoryEntity>,
        books: List<BookEntity>,
        stores: List<StoreEntity>,
    ): EmptyDataResult<DataError.Local>

    suspend fun getBookCategories(): Flow<Result<List<CategoryEntity>, DataError.Local>>
    suspend fun getBooksByCategory(categoryName: String): Flow<Result<List<BookEntity>, DataError.Local>>
    suspend fun getStoresWithBook(bookId: Int): Flow<Result<List<StoreEntity>, DataError.Local>>
}


internal class BooksDbClientImpl(
    private val booksDao: BooksDao,
    private val dispatcherProvider: DispatcherProvider,
) : BooksDbClient {

    override suspend fun insertBooksOverview(
        categories: List<CategoryEntity>,
        books: List<BookEntity>,
        stores: List<StoreEntity>,
    ): EmptyDataResult<DataError.Local> {
        return safeDbCall(dispatcherProvider.io) {
            booksDao.insertBooksOverview(categories, books, stores)
        }
    }

    override suspend fun getBookCategories(): Flow<Result<List<CategoryEntity>, DataError.Local>> {
        return safeDbFlowCall(dispatcherProvider.io) {
            booksDao.getBookCategories()
        }
    }

    override suspend fun getBooksByCategory(categoryName: String): Flow<Result<List<BookEntity>, DataError.Local>> {
        return safeDbFlowCall(dispatcherProvider.io) {
            booksDao.getBooksByCategory(categoryName)
        }
    }

    override suspend fun getStoresWithBook(bookId: Int): Flow<Result<List<StoreEntity>, DataError.Local>> {
        return safeDbFlowCall(dispatcherProvider.io) {
            booksDao.getStoresWithBook(bookId)
        }
    }

}
