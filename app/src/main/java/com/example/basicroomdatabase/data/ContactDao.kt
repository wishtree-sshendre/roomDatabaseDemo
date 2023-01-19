package com.example.basicroomdatabase.data

import android.icu.text.CaseMap
import android.os.FileObserver.DELETE
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)


    @Query("DELETE FROM Note WHERE noteId= :id")
    suspend fun delete(id:Long)

    @Query("UPDATE Note set catId=:id, noteTitle=:title, nDesc=:pNumber,date=:date,time=:time, fDate=:fDate, image=:image  WHERE noteId=:nid ")
    suspend fun update(id: Long,   title: String, date: String, time: String, pNumber:String, fDate: Date, image: String,nid:Long)
//    @Update
//    suspend fun updateContact(contact: Contact)


//    var phoneNo: String?,
//    var date: String,
//    var time:String,
//    var fdate: Date,
//    var hour:Int?,




    @Query("SELECT * FROM Note")
    fun readAllData(): LiveData<List<Contact?>?>

    @Query("SELECT * FROM Note INNER JOIN Category ON Note.catId = Category.categoryId")
    fun searchContent(): LiveData<List<JoinClass?>?>

}