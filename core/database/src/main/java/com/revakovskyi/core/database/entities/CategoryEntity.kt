package com.revakovskyi.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "category_name") val name: String,
    @ColumnInfo(name = "updating_frequency") val updatingFrequency: String,
    @ColumnInfo(name = "published_date") val publishedDate: String,
)
