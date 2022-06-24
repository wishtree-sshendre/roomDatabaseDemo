package com.example.basicroomdatabase.data

import androidx.room.TypeConverter
import java.util.*

class Convertor {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toCalendar(l: Long?): Calendar? =
        if (l == null) null else Calendar.getInstance().apply { timeInMillis = l }

    @TypeConverter
    fun fromCalendar(c: Calendar?): Long? = c?.time?.time
}