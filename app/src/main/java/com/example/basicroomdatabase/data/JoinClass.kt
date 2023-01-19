package com.example.basicroomdatabase.data

import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

data class JoinClass constructor(
    var nid: Long,
    var name: String?,
    var phoneNo: String?,
    var date: String,
    var time:String,
    var fdate: Date,
    var hour:Int?,
    var min:Int?,
    var cid: Long,
    var Title: String?,
    var cColor: String?,
)



