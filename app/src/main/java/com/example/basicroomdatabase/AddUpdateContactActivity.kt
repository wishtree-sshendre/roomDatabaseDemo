package com.example.basicroomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.ContactDataBase
import com.example.basicroomdatabase.databinding.ActivityAddUpdateContactBinding
import com.example.basicroomdatabase.view.MyViewModel
import com.example.basicroomdatabase.view.ViewModelFactory

class AddUpdateContactActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUpdateContactBinding
    private lateinit var viewModel: MyViewModel
    lateinit var database: ContactDataBase
       var cid: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_contact)
        binding= ActivityAddUpdateContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = ContactDataBase.getDatabase(this)
        val dao = database.contactDao()
        val repository = ContactRepository(dao)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(MyViewModel::class.java)

        val taskType = intent.getStringExtra("Task")

        if(taskType.equals("Edit"))
        {
            val name = intent.getStringExtra("nameText")
            val phone=intent.getStringExtra("phoneText")
            cid= intent.getLongExtra("id",-1)

            binding.nameField.setText(name)
            binding.pnoField.setText(phone)

            binding.button.setText("Update")

        }

        else{
            binding.button.setText("ADD")
        }


        binding.button.setOnClickListener {

            if(binding.nameField.text.isNotEmpty()&&binding.pnoField.text.isNotEmpty()) {
                if (taskType.equals("Edit")) {
                    viewModel.editContactlist(
                        Contact(
                            cid,
                            binding.nameField.text.toString(),
                            binding.pnoField.text.toString()
                        )
                    )
                }
            else{
                viewModel.insertNote(Contact(0,binding.nameField.text.toString(),binding.pnoField.text.toString()))
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }else{
                binding.nameField.error ="name is required"
                binding.pnoField.error="Must enter phone number"

            }        }



    }
}