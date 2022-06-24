package com.example.basicroomdatabase

import androidx.lifecycle.LiveData
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.ContactDao
import com.example.basicroomdatabase.data.JoinClass
import java.util.*

class ContactRepository (private  val contactDao: ContactDao){

    val allNotes : LiveData<List<Contact?>?> = contactDao.readAllData()
    val joindata: LiveData<List<JoinClass?>?>? = contactDao.searchContent()
    suspend fun insertData(contact: Contact){
       contactDao.insertContact(contact)
    }

    suspend fun delete(id: Long){
        contactDao.delete(id)
    }

    suspend fun update(id: Long, name: String, date: String, time: String, pNumber:String, fDate: Date){
        contactDao.update(id, name, date, time, pNumber, fDate)
    }




}