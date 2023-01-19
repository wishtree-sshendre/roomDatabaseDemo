package com.example.basicroomdatabase

import androidx.lifecycle.LiveData
import com.example.basicroomdatabase.data.CategoryDao
import com.example.basicroomdatabase.data.Catergory
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.JoinClass

class CategoryRepository (private var catergoryDao: CategoryDao){
    val allDatas : LiveData<List<Catergory>> = catergoryDao.readAllData()

    suspend fun insertData(catergory: Catergory){
        catergoryDao.insertContact(catergory)
    }

    suspend fun delete(catergory: Catergory){
        catergoryDao.delete(catergory)
    }

    suspend fun update(catergory: Catergory){
        catergoryDao.updateContact(catergory)
    }
}