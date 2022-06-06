package com.example.basicroomdatabase

import androidx.lifecycle.LiveData
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.ContactDao

class ContactRepository (private  val contactDao: ContactDao){
    val allNotes : LiveData<List<Contact>> = contactDao.readAllData()

    suspend fun insertData(contact: Contact){
       contactDao.insertContact(contact)
    }

    suspend fun delete(contact: Contact){
        contactDao.delete(contact)
    }

    suspend fun update(contact: Contact){
        contactDao.updateContact(contact)
    }


}