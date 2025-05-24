package com.revakovskyi.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "books_by_category",
    indices = [Index(value = ["category_name"])],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["category_name"],
            childColumns = ["category_name"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id") val id: Int = 0,
    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publisher") val publisher: String,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "rank") val rank: Int,
)
