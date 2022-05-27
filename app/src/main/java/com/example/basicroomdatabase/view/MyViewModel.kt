package com.example.basicroomdatabase.view
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicroomdatabase.ContactRepository
import com.example.basicroomdatabase.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(private val repo: ContactRepository): ViewModel() {

        val allContacts: LiveData<List<Contact>>
            get() = repo.allNotes

        fun insertNote( contact: Contact){

            viewModelScope.launch(Dispatchers.IO) {
                repo.insertData(contact)
            }
        }

    fun deleteNotes(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(contact)
        }
    }
    }

