package com.revakovskyi.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "stores_with_book",
    indices = [Index(value = ["book_title"])],
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["book_title"],
            childColumns = ["book_title"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class StoreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book_title") val bookTitle: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String,
)
