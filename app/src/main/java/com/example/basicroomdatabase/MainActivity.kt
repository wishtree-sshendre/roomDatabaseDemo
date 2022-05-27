package com.example.basicroomdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.ContactDataBase
import com.example.basicroomdatabase.databinding.ActivityMainBinding
import com.example.basicroomdatabase.view.MyViewModel
import com.example.basicroomdatabase.view.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var database: ContactDataBase
    private lateinit var viewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recView.layoutManager = LinearLayoutManager(this)
        val adapter = ViewAdapter(this, this)
        binding.recView.adapter = adapter

        database = ContactDataBase.getDatabase(this)
        val dao = database.contactDao()
        val repository = ContactRepository(dao)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(MyViewModel::class.java)

        viewModel.allContacts.observe(this, Observer {
            adapter.updateContact(it)
        })

        binding.submitBtn.setOnClickListener {

            if(binding.name.text.isNotEmpty() && binding.phoneNo.text.isNotEmpty()) {

                viewModel.insertNote(
                    Contact(
                        0,
                        binding.name.text.toString(),
                        binding.phoneNo.text.toString()
                    )
                )
            }


        }
    }

    fun onItemClicked(contact: Contact) {
        viewModel.deleteNotes(contact)
    }

}

