package com.example.basicroomdatabase.view
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicroomdatabase.ContactRepository
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.JoinClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MyViewModel(private val repo: ContactRepository): ViewModel() {

        val allContacts: LiveData<List<Contact?>?>
            get() = repo.allNotes
    val joindata: LiveData<List<JoinClass?>?>?
        get() = repo.joindata


        fun insertNote( contact: Contact){

            viewModelScope.launch(Dispatchers.IO) {
                repo.insertData(contact)
            }
        }
    fun editContactlist(
        cid: Long,
        title: String,
        date: String,
        time: String,
        desc: String,
        fDate: Date,
        image:String,
        noteId: Long
    ){
        viewModelScope.launch (Dispatchers.IO){
           repo.update(cid, title, date, time, desc, fDate,image, noteId)
        }
    }

    fun deleteNotes(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(id)
        }




    }






    }

