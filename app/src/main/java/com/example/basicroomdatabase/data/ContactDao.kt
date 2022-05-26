package com.example.basicroomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basicroomdatabase.data.Contact

@Dao
interface ContactDao {

    @Insert
    suspend fun insertUser(vararg contact: Contact)

    @Query("SELECT * FROM contact_Info")
    fun readAllData(): LiveData<List<Contact>>

}