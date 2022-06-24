package com.example.basicroomdatabase.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicroomdatabase.CategoryRepository
import com.example.basicroomdatabase.ContactRepository
import com.example.basicroomdatabase.data.Catergory
import com.example.basicroomdatabase.data.JoinClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(private val repo: CategoryRepository): ViewModel() {
    val allCatData: LiveData<List<Catergory>>
        get() = repo.allDatas


    fun insertCategory( category: Catergory){

        viewModelScope.launch(Dispatchers.IO) {
            repo.insertData(category)
        }
    }
}