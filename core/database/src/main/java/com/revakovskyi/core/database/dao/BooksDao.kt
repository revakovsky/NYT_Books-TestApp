package com.revakovskyi.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.revakovskyi.core.database.entities.BookEntity
import com.revakovskyi.core.database.entities.CategoryEntity
import com.revakovskyi.core.database.entities.StoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BooksDao {

    @Transaction
    suspend fun insertBooksOverview(
        categories: List<CategoryEntity>,
        books: List<BookEntity>,
        stores: List<StoreEntity>,
    ) {
        clearAllBooksOverviewData()

        insertBooksCategories(categories)
        insertBooks(books)
        insertStores(stores)
    }


    @Upsert
    suspend fun insertBooksCategories(categories: List<CategoryEntity>)

    @Upsert
    suspend fun insertBooks(books: List<BookEntity>)

    @Upsert
    suspend fun insertStores(stores: List<StoreEntity>)


    @Query("SELECT * FROM categories")
    fun getBookCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM books_by_category WHERE category_name = :categoryName")
    fun getBooksByCategory(categoryName: String): Flow<List<BookEntity>>

    @Query("SELECT * FROM stores_with_book WHERE book_id = :bookId")
    fun getStoresWithBook(bookId: String): Flow<List<StoreEntity>>

    @Transaction
    suspend fun clearAllBooksOverviewData() {
        deleteStores()
        deleteBooks()
        deleteCategories()
    }

    @Query("DELETE FROM stores_with_book")
    suspend fun deleteStores()

    @Query("DELETE FROM books_by_category")
    suspend fun deleteBooks()

    @Query("DELETE FROM categories")
    suspend fun deleteCategories()

}
