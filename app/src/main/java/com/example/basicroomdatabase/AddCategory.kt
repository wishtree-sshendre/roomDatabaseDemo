package com.example.basicroomdatabase

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.basicroomdatabase.data.Catergory
import com.example.basicroomdatabase.data.ContactDataBase
import com.example.basicroomdatabase.databinding.ActivityAddCatogoryBinding
import com.example.basicroomdatabase.databinding.ActivityAddUpdateContactBinding
import com.example.basicroomdatabase.view.CatViewModelFactory
import com.example.basicroomdatabase.view.CategoryViewModel
import com.example.basicroomdatabase.view.MyViewModel
import com.example.basicroomdatabase.view.ViewModelFactory

class AddCategory : AppCompatActivity() {
    lateinit var binding: ActivityAddCatogoryBinding
    lateinit var color:String
    private lateinit var viewModel: CategoryViewModel
    lateinit var database: ContactDataBase
     var isSelected:Boolean ?= false
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCatogoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isSelected=false
        database = ContactDataBase.getDatabase(this)
        val dao = database.categoryDao()
        val repository = CategoryRepository(dao)
        viewModel =
            ViewModelProvider(this, CatViewModelFactory(repository)).get(CategoryViewModel::class.java)
        binding.c1.setOnClickListener {
            color ="#f1c0e8"
            binding.img1.setVisibility(View.VISIBLE)
            binding.img2.setVisibility(View.INVISIBLE)
            binding.img3.setVisibility(View.INVISIBLE)
            binding.img4.setVisibility(View.INVISIBLE)
            binding.img5.setVisibility(View.INVISIBLE)
        }
        binding.c2.setOnClickListener {
            color ="#ffb700"
            binding.img2.visibility = View.VISIBLE
            binding.img1.setVisibility(View.INVISIBLE)
            binding.img3.setVisibility(View.INVISIBLE)
            binding.img4.setVisibility(View.INVISIBLE)
            binding.img5.setVisibility(View.INVISIBLE)

        }
        binding.c3.setOnClickListener {
            color ="#90dbf4"
            binding.img3.visibility = View.VISIBLE
            binding.img1.setVisibility(View.INVISIBLE)
            binding.img2.setVisibility(View.INVISIBLE)
            binding.img4.setVisibility(View.INVISIBLE)
            binding.img5.setVisibility(View.INVISIBLE)
        }
        binding.c4.setOnClickListener {
            color ="#b5e48c"
            binding.img4.visibility = View.VISIBLE
            binding.img1.setVisibility(View.INVISIBLE)
            binding.img3.setVisibility(View.INVISIBLE)
            binding.img2.setVisibility(View.INVISIBLE)
            binding.img5.setVisibility(View.INVISIBLE)
        }
        binding.c5.setOnClickListener {
            color ="#f79188"
            binding.img5.visibility = View.VISIBLE
            binding.img1.setVisibility(View.INVISIBLE)
            binding.img3.setVisibility(View.INVISIBLE)
            binding.img4.setVisibility(View.INVISIBLE)
            binding.img2.setVisibility(View.INVISIBLE)
        }

        binding.addBtn.setOnClickListener {
            if(binding.titleText.text.isNotEmpty() && color.isNotEmpty()) {
                viewModel.insertCategory(Catergory(0, binding.titleText.text.toString(), color))
                startActivity(Intent(applicationContext, MainActivity::class.java))
                this.finish()
            }
            else{
                Toast.makeText(this,"please Enter the categort and select the color",Toast.LENGTH_SHORT).show()
            }
        }


    }
}