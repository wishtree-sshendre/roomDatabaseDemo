package com.example.basicroomdatabase.data

import androidx.room.TypeConverter
import java.util.*

class Convertor {

    @TypeConverter
    fun fromDatetoLong (value:Date):Long{
       return value.time
    }

    @TypeConverter
    fun fromLongtoDate (value:Long):Date{
       return Date(value)
    }
}