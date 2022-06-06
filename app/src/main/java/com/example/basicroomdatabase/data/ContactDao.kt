package com.example.basicroomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)


    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)


    @Query("SELECT * FROM contact_Info")
    fun readAllData(): LiveData<List<Contact>>

}