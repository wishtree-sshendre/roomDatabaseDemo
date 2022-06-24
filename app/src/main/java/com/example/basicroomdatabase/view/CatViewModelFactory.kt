package com.example.basicroomdatabase.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basicroomdatabase.CategoryRepository
import com.example.basicroomdatabase.ContactRepository
import com.example.basicroomdatabase.data.Catergory

class CatViewModelFactory (private val repository: CategoryRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(repository) as T
    }


}