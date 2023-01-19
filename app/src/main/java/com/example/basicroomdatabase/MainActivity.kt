package com.example.basicroomdatabase

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicroomdatabase.data.ContactDataBase
import com.example.basicroomdatabase.data.JoinClass
import com.example.basicroomdatabase.databinding.ActivityMainBinding
import com.example.basicroomdatabase.view.MyViewModel
import com.example.basicroomdatabase.view.ViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var database: ContactDataBase
    private var id: Long = 0
    private lateinit var viewModel: MyViewModel
    var isAllFabsVisible: Boolean? = null
    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addTask.setVisibility(View.GONE)
        binding.addCategory.setVisibility(View.GONE)
        isAllFabsVisible = false
        binding.recView.layoutManager = LinearLayoutManager(this)
        val adapter = ViewAdapter(this, this)
        binding.recView.adapter = adapter
        binding.fab.setOnClickListener {
            if (!isAllFabsVisible!!) {
                binding.addTask.show()
                binding.addCategory.show()
                isAllFabsVisible = true
            } else {
                binding.addTask.hide()
                binding.addCategory.hide()
                isAllFabsVisible = false
            }
        }


        binding.addTask.setOnClickListener {
            val intent = Intent(this, AddUpdateContactActivity::class.java)
            startActivity(intent)
            binding.addTask.hide()
            binding.addCategory.hide()
        }
        binding.addCategory.setOnClickListener {
            val intent = Intent(this, AddCategory::class.java)
            startActivity(intent)
            binding.addTask.hide()
            binding.addCategory.hide()
        }

        database = ContactDataBase.getDatabase(this)
        val dao = database.contactDao()
        val repository = ContactRepository(dao)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(MyViewModel::class.java)


        viewModel.joindata?.observe(this, Observer {
                       var f=it?.sortedWith(compareBy { it?.fDate})
                       if (f != null) {
                           adapter.updateContact(f as List<JoinClass>)
                       }

                       if (it != null) {
                           if(it?.isEmpty()){
                               binding.dd.visibility=View.VISIBLE
                           } else{
                               binding.dd.visibility= View.GONE

                           }
                       }
                   })

        viewModel.joindata?.observe(this, Observer{
            println("*******+$it")
        })


    }


    fun onItemClicked(id: Long) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationClass::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, id.toInt(), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.cancel(pendingIntent)

        viewModel.deleteNotes(id)

    }


    fun onclicked(joinClass:JoinClass) {

        val intent = Intent(this, AddUpdateContactActivity::class.java)

        intent.putExtra("Task", "Edit")
        intent.putExtra("nameText", joinClass.noteTitle)
        intent.putExtra("phoneText", joinClass.nDesc)
        intent.putExtra("catId", joinClass.categoryId)
        intent.putExtra("date", joinClass.date)
        intent.putExtra("time", joinClass.time)
        intent.putExtra("category",joinClass.catTitle)
        intent.putExtra("time24",joinClass.fDate)
        intent.putExtra("uriImage",joinClass.image)
        intent.putExtra("noteId",joinClass.noteId)
//        intent.putExtra("h1",contact.category)
//        intent.putExtra("m1",contact.category)
        startActivity(intent)
        binding.addTask.hide()
        binding.addCategory.hide()

    }


}


