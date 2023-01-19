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
    fun editContactlist(id: Long, name: String, date: String, time: String, pNumber:String, fDate: Date){
        viewModelScope.launch (Dispatchers.IO){
           repo.update(id, name, date, time, pNumber, fDate)
        }
    }

    fun deleteNotes(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(id)
        }




    }






    }

