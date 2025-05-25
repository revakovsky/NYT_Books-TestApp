package com.revakovskyi.books.data

import com.revakovskyi.books.data.mappers.toBook
import com.revakovskyi.books.data.mappers.toBookEntities
import com.revakovskyi.books.data.mappers.toCategory
import com.revakovskyi.books.data.mappers.toCategoryEntities
import com.revakovskyi.books.data.mappers.toStore
import com.revakovskyi.books.data.mappers.toStoreEntities
import com.revakovskyi.books.domain.BooksRepository
import com.revakovskyi.core.database.BooksDbClient
import com.revakovskyi.core.domain.books.Book
import com.revakovskyi.core.domain.books.Category
import com.revakovskyi.core.domain.books.Store
import com.revakovskyi.core.domain.utils.DataError
import com.revakovskyi.core.domain.utils.DispatcherProvider
import com.revakovskyi.core.domain.utils.EmptyDataResult
import com.revakovskyi.core.domain.utils.Result
import com.revakovskyi.core.domain.utils.asEmptyDataResult
import com.revakovskyi.core.network.BooksNetworkClient
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BooksRepositoryImpl(
    private val dbClient: BooksDbClient,
    private val networkClient: BooksNetworkClient,
    private val dispatcherProvider: DispatcherProvider,
) : BooksRepository {

    override fun getBookCategories(forceRefresh: Boolean): Flow<Result<List<Category>, DataError>> {
        return if (forceRefresh) forceLoadCategories()
        else loadFromCacheOrFetch()
    }

    override fun getBooksByCategory(
        categoryName: String,
        forceRefresh: Boolean,
    ): Flow<Result<List<Book>, DataError>> {
        return if (forceRefresh) {
            flow {
                when (val result = fetchAndCacheBooksOverview()) {
                    is Result.Error -> emit(Result.Error(result.error))
                    is Result.Success -> loadBooksFromCache(categoryName).collect { emit(it) }
                }
            }
        } else loadBooksFromCache(categoryName)
    }

    override fun getStoresWithBook(bookTitle: String): Flow<Result<List<Store>, DataError>> {
        return dbClient.getStoresWithBook(bookTitle).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> {
                    val stores = result.data.map { it.toStore() }
                    Result.Success(stores)
                }
            }
        }
    }

    override suspend fun clearDb(): EmptyDataResult<DataError.Local> {
        return dbClient.clearDb()
    }

    private fun forceLoadCategories(): Flow<Result<List<Category>, DataError>> = flow {
        when (val result = fetchAndCacheBooksOverview()) {
            is Result.Error -> emit(Result.Error(result.error))
            is Result.Success -> {
                val cached = loadCategoriesFromCache().first()
                emit(cached)
            }
        }
    }

    private fun loadFromCacheOrFetch(): Flow<Result<List<Category>, DataError>> = flow {
        val cached = loadCategoriesFromCache().first()

        if (cached is Result.Success && cached.data.isEmpty()) emitAll(forceLoadCategories())
        else emit(cached)
    }

    private suspend fun fetchAndCacheBooksOverview(): EmptyDataResult<DataError> {
        return when (val networkCallResult = networkClient.fetchBooksOverview()) {
            is Result.Error -> networkCallResult.asEmptyDataResult()
            is Result.Success -> {
                val booksOverviewDto = networkCallResult.data

                withContext(dispatcherProvider.io) {
                    val categoriesDeferred = async { booksOverviewDto.toCategoryEntities() }
                    val booksDeferred = async { booksOverviewDto.toBookEntities() }
                    val storesDeferred = async { booksOverviewDto.toStoreEntities() }

                    dbClient.insertBooksOverview(
                        categoriesDeferred.await(),
                        booksDeferred.await(),
                        storesDeferred.await(),
                    )
                }
            }
        }
    }

    private fun loadCategoriesFromCache(): Flow<Result<List<Category>, DataError>> {
        return dbClient.getBookCategories().map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> {
                    val categories = result.data.map { it.toCategory() }
                    Result.Success(categories)
                }
            }
        }
    }

    private fun loadBooksFromCache(categoryName: String): Flow<Result<List<Book>, DataError>> {
        return dbClient.getBooksByCategory(categoryName).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> {
                    val books = result.data.map { it.toBook() }
                    Result.Success(books)
                }
            }
        }
    }

}
