package com.example.basicroomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.basicroomdatabase.data.Contact

@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact: Contact)
    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contact_Info")
    fun readAllData(): LiveData<List<Contact>>

}