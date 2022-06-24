package com.example.basicroomdatabase.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basicroomdatabase.CategoryRepository
import com.example.basicroomdatabase.ContactRepository

class ViewModelFactory (private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyViewModel(repository) as T
        }
    }
