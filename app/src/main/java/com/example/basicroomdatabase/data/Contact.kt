package com.example.basicroomdatabase.data

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "Note")
data class Contact @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long,
    var noteTitle: String?,
    var nDesc: String?,
    var date: String,
    var time:String,
    @TypeConverters(Convertor::class)
    var fDate: Date,
    var hour:Int?,
    var min:Int?,
    var catId: Long,
    val image: String,
)

@Entity(tableName = "join_table",
    foreignKeys = arrayOf(
        ForeignKey(
        entity = Contact::class,
        parentColumns = arrayOf("catId"),
        childColumns = arrayOf("categoryId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate= ForeignKey.CASCADE

    )
    )
)

@SuppressLint("NewApi")
object TiviTypeConverters {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}
