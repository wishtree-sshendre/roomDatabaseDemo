package com.example.basicroomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_Info")
data class Catergory(
    @PrimaryKey(autoGenerate = true)
    var cid: Long,
    var Title: String?,
    var cColor: String?,
)
