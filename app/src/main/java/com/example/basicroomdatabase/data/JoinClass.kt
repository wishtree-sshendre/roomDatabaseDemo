package com.example.basicroomdatabase.data

import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

data class JoinClass constructor(
    var noteId: Long,
    var noteTitle: String?,
    var nDesc: String?,
    var date: String,
    var time:String,
    var fDate: Date,
    var hour:Int?,
    var min:Int?,
    val image: String,
    var categoryId: Long,
    var catTitle: String?,
    var catColor: String?,
)



