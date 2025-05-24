package com.revakovskyi.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.revakovskyi.core.database.dao.BooksDao
import com.revakovskyi.core.database.entities.BookEntity
import com.revakovskyi.core.database.entities.CategoryEntity
import com.revakovskyi.core.database.entities.StoreEntity

@Database(
    entities = [CategoryEntity::class, BookEntity::class, StoreEntity::class],
    version = 1,
)
internal abstract class BooksDb : RoomDatabase() {

    abstract val booksDao: BooksDao

}
