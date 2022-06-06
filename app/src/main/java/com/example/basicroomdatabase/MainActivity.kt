package com.example.basicroomdatabase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.ContactDataBase
import com.example.basicroomdatabase.databinding.ActivityMainBinding
import com.example.basicroomdatabase.view.MyViewModel
import com.example.basicroomdatabase.view.ViewModelFactory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    lateinit var database: ContactDataBase
    lateinit var database2: DatabaseReference
     private var id:Long=0
    private lateinit var viewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recView.layoutManager = LinearLayoutManager(this)
        val adapter = ViewAdapter(this, this)
        binding.recView.adapter = adapter

        binding.fab.setOnClickListener {
        val intent =Intent(this,AddUpdateContactActivity::class.java)
            startActivity(intent)
            this.finish()
        }


        database = ContactDataBase.getDatabase(this)
        val dao = database.contactDao()
        val repository = ContactRepository(dao)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(MyViewModel::class.java)
        viewModel.allContacts.observe(this, Observer {
            adapter.updateContact(it)
        })

//        binding.updateBtn.setOnClickListener{
//            viewModel.editContactlist(Contact(id,binding.name.text.toString(),binding.phoneNo.text.toString()))
//            binding.name.text.clear()
//            binding.phoneNo.text.clear()
//
//        }

//        binding.submitBtn.setOnClickListener {
//
//            if (binding.name.text.isNotEmpty() && binding.phoneNo.text.isNotEmpty()) {
//                viewModel.insertNote(
//                    Contact(
//                        0,
//                        binding.name.text.toString(),
//                        binding.phoneNo.text.toString(),
//                    )
//
//                )
//

    }



    fun onItemClicked(contact: Contact) {
        viewModel.deleteNotes(contact)

    }



    fun onclicked(contact: Contact) {

        val intent =Intent(this,AddUpdateContactActivity::class.java)

        intent.putExtra("Task","Edit")
        intent.putExtra("nameText",contact.name)
        intent.putExtra("phoneText",contact.phoneNo)
        intent.putExtra("id",contact.cid)
        startActivity(intent)
        this.finish()



//        var name = contact.name
//        var phoneno = contact.phoneNo
//           id = contact.cid!!
//        binding.name.setText(name)
//        binding.phoneNo.setText(phoneno)

    }





}


