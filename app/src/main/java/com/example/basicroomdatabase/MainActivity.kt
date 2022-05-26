package com.example.basicroomdatabase

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.ContactDataBase
import com.example.basicroomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var database: ContactDataBase
    lateinit var postArrayList: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = ContactDataBase.getDatabase(this@MainActivity)
        binding.submitBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                async {
                    database.contactDao().insertUser(
                        Contact(
                            0,
                            binding.name.text.toString(),
                            binding.phoneNo.text.toString()
                        )
                    )
                }.await()
            }
            getdata()

        }





    }

    private fun getdata() {
        database.contactDao().readAllData().observe(this, Observer {
            Log.d("data****",it.toString())
        })
    }



}