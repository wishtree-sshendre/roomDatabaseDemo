package com.example.basicroomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(catergory: Catergory)


    @Delete
    suspend fun delete(catergory: Catergory)

    @Update
    suspend fun updateContact(catergory: Catergory)


    @Query("SELECT * FROM category_Info")
    fun readAllData(): LiveData<List<Catergory>>

    @Query("SELECT cColor FROM category_Info WHERE Title = (:category)")
    suspend fun loadAllUsersOlderThan(category:String): String

}