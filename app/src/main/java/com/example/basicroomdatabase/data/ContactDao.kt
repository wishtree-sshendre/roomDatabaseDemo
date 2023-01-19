package com.example.basicroomdatabase.data

import android.os.FileObserver.DELETE
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)


    @Query("DELETE FROM contact_Info WHERE nid= :id")
    suspend fun delete(id:Long)

    @Query("UPDATE contact_Info set c_id=:id, name=:name, phoneNo=:pNumber,date=:date,time=:time, fdate=:fDate")
    suspend fun update(id: Long, name: String, date: String, time: String, pNumber:String, fDate: Date)
//    @Update
//    suspend fun updateContact(contact: Contact)


//    var phoneNo: String?,
//    var date: String,
//    var time:String,
//    var fdate: Date,
//    var hour:Int?,




    @Query("SELECT * FROM contact_Info")
    fun readAllData(): LiveData<List<Contact?>?>

    @Query("SELECT * FROM contact_Info INNER JOIN category_Info ON contact_Info.c_id = category_Info.cid")
    fun searchContent(): LiveData<List<JoinClass?>?>

}