package com.example.basicroomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "contact_Info")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val cid: Long,
    val name: String,
    val phoneNo: String,
)

