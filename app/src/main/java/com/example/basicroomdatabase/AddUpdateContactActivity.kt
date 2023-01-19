package com.example.basicroomdatabase

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.TypeConverter
import com.example.basicroomdatabase.data.Catergory
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.ContactDataBase
import com.example.basicroomdatabase.databinding.ActivityAddUpdateContactBinding
import com.example.basicroomdatabase.view.CatViewModelFactory
import com.example.basicroomdatabase.view.CategoryViewModel
import com.example.basicroomdatabase.view.MyViewModel
import com.example.basicroomdatabase.view.ViewModelFactory

import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*


class AddUpdateContactActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUpdateContactBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var catViewModel: CategoryViewModel
    lateinit var database: ContactDataBase
    var cid: Long = -1
    var sid: Int = 0
    lateinit var datef: Date
    lateinit var selectedTime: String
    lateinit var catDataList: List<Catergory>
    lateinit var joinDataList: List<Catergory>
    lateinit var category: String
    lateinit var scolor: String
    lateinit var taskType: String
    lateinit var calendar: Calendar
    lateinit var concDate: String
    var catid:Long=0
    private lateinit var alarmManager: AlarmManager
    private lateinit var reminderBrodcast: NotificationClass
    private lateinit var pendingIntent: PendingIntent
    private lateinit var catRepo :CategoryRepository
    var time24: String = ""
    var title1:String = ""
    var des1:String = ""
    var fullDate: String=""
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_contact)

        // var catDataList = List<Catergory>()
        var hourf: Int = 0
        var minf: Int = 0
        var secf: Int = 0
        var dayf: Int = 0
        var monf: Int = 0
        var yearf: Int = 0
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        var id: Int = 0
        binding = ActivityAddUpdateContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskType = intent.getStringExtra("Task").toString()

        category = intent.getStringExtra("category").toString()
        var catList: ArrayList<String> = arrayListOf()
        createNotificationChannel()
        database = ContactDataBase.getDatabase(this)
        val catDao = database.categoryDao()
        val dao = database.contactDao()
         catRepo = CategoryRepository(catDao)
        val repository = ContactRepository(dao)
        catViewModel =
            ViewModelProvider(this, CatViewModelFactory(catRepo)).get(CategoryViewModel::class.java)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(MyViewModel::class.java)

        val spinner = findViewById<Spinner>(R.id.spin_dd)
        val arrayCatergory: ArrayList<String> = ArrayList()

//        arrayList.add("Select")


        binding.setDateBtn.setOnClickListener {

            val c2 = Calendar.getInstance()
            val imm: InputMethodManager =
                this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            var view: View? = this.currentFocus

            if (view == null) {
                view = View(this)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)


            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, myear: Int, mMonth: Int, mDay: Int ->

                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    calendar[myear, mMonth] = mDay
                    monf = mMonth
                    yearf = myear
                    dayf = mDay

                    datef = calendar.time
                    fullDate = sdf.format(datef)

                    binding.setDateBtn.setText(fullDate)

                },
                year,
                month,
                day
            )
            dpd.datePicker.minDate = c2.timeInMillis
            dpd.show()
        }
        binding.setTimeBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val tpd = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view: TimePicker, mhour: Int, mMinute: Int ->
                    //it's after current
                    var t1: String = ""

                    calendar[Calendar.HOUR_OF_DAY] = mhour
                    calendar[Calendar.MINUTE] = mMinute
                    hourf = mhour
                    minf = mMinute
                    if (mhour < 10) {
                        if (mMinute < 10) {
                            t1 = "0$mhour:0$mMinute"
                        } else {
                            t1 = "0$mhour:$mMinute"
                        }

                    } else {
                        t1 = "$mhour:$mMinute"
                    }

                    time24 = t1
                    println("******* + $time24")

                    val hour: Int = mhour % 12
                    binding.setTimeBtn.setText(
                        java.lang.String.format(
                            "%02d:%02d %s", if (hour == 0) 12 else hour,
                            mMinute, if (mhour < 12) {
                                "a.m."
                            } else {
                                "p.m."
                            }
                        )
                    )
                    selectedTime = java.lang.String.format(
                        "%02d:%02d %s", if (hour == 0) 12 else hour,
                        mMinute, if (mhour < 12) "a.m." else "p.m."
                    )
                },
                hour,
                min,
                true
            )
            tpd.show()

        }

        catViewModel.allCatData.observe(this, {

            arrayCatergory.add("Defualt")
            it.forEach {
                arrayCatergory.add(it.Title.toString())
            }
            catDataList = it
            for (i in arrayCatergory.indices) {
                if (arrayCatergory[i].equals(category)) {
                    sid = i
                }
            }
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayCatergory)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
            if (taskType.equals("Edit")) {
                spinner.setSelection(sid)
            }else{
                spinner.setSelection(0)
            }

        })


//        calendar.set(yearf,monf,dayf,hourf,minf)
//        println("###### ${calendar.get(Calendar.DAY_OF_MONTH)},${calendar.get(Calendar.MONTH)},${calendar.get(Calendar.YEAR)},${calendar.get(Calendar.HOUR_OF_DAY)},${calendar.get(Calendar.MINUTE)}")


        spinner.onItemSelectedListener = getCategory()

        if (taskType.equals("Edit")) {
            val name = intent.getStringExtra("nameText")
            val phone = intent.getStringExtra("phoneText")
            val stime = intent.getStringExtra("time")
            var sdate = intent.getStringExtra("date")
            val scategory = intent.getStringExtra("category")
            val wdate = intent.getStringExtra("time24")
            val cid = intent.getLongExtra("id",-1)
            val catsid = intent.getLongExtra("catid",-1)
            //val h = intent.
            val fdate = Date(intent.getLongExtra("fdate", -1))

            Log.e("******", fdate.toString())
            datef = fdate
            calendar.time= fdate

            if (sdate != null) {
                fullDate = sdate
            }
            if (stime != null) {
                selectedTime = stime
            }
            if (scategory != null) {
                category = scategory
            }
            if (wdate != null) {
                concDate = wdate
            }
            if(catsid != null){
                catid = catsid
            }


            binding.titleField.setText(name)
            binding.descField.setText(phone)
            binding.setDateBtn.setText(fullDate)
            binding.setTimeBtn.setText(selectedTime)
            binding.button.setText("Update")


        } else {
            binding.button.setText("ADD")
        }

        binding.button.setOnClickListener {
            if (binding.titleField.text.isNotEmpty() && binding.descField.text.isNotEmpty()&& selectedTime.isNotEmpty()) {
                scheduleNotification()
                if (taskType.equals("Edit")) {
                    viewModel.editContactlist(
                        catid,
                        binding.titleField.text.toString(),
                        fullDate,
                        selectedTime,
                        binding.descField.text.toString(),
                        calendar.time
                    )
                  this.finish()
                } else {

                    viewModel.insertNote(
                        Contact(
                            0,
                            binding.titleField.text.toString(),
                            binding.descField.text.toString(),
                            fullDate,
                            selectedTime,
                            calendar.time,
                            hourf,
                            minf,
                            catid
                        )
                    )
              this.finish()
                }
            }
            else {
                binding.titleField.error = "name is required"
                binding.descField.error = "Must enter phone number"

            }
                        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, NotificationClass::class.java)
        val title = binding.titleField .text.toString()
        val message = binding.descField.text.toString()
        intent.putExtra("title_Extra", title)
        intent.putExtra("desc_Extra", message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = calendar.time
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

    }

    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelID, name, importance)

            channel.description = desc
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        }







    fun getColor(arrayList: List<Catergory>, cat: String) {
        for (i in arrayList.indices) {
            if (i == 0) {
                scolor = "#94d0f7"
            }
            if (arrayList[i].Title.equals(category)) {
                scolor = catDataList[i].cColor.toString()
            }

        }
    }

        fun getId(arrayList: List<Catergory>, cat: String) {

            for (i in arrayList.indices) {
                if (arrayList[i].Title.equals(cat)) {
                    catid = catDataList[i].cid
                }
            }

        }


        fun getCategory(): AdapterView.OnItemSelectedListener? {
            return object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    category = parent.getItemAtPosition(position).toString()
                    Log.d("Cat Data list 3", "$catDataList")
                    getColor(catDataList, category)
                    getId(catDataList,category)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(
                        this@AddUpdateContactActivity,
                        "please select category",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        }
    }






