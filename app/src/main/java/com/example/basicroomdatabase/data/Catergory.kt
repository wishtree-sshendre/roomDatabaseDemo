package com.example.basicroomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Catergory(
    @PrimaryKey(autoGenerate = true)
    var categoryId: Long,
    var catTitle: String?,
    var catColor: String?,
)
