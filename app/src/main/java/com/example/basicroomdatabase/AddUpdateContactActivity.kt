package com.example.basicroomdatabase

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.icu.text.DateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.basicroomdatabase.data.Catergory
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.ContactDataBase
import com.example.basicroomdatabase.databinding.ActivityAddUpdateContactBinding
import com.example.basicroomdatabase.view.CatViewModelFactory
import com.example.basicroomdatabase.view.CategoryViewModel
import com.example.basicroomdatabase.view.MyViewModel
import com.example.basicroomdatabase.view.ViewModelFactory
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class AddUpdateContactActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUpdateContactBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var catViewModel: CategoryViewModel
    lateinit var database: ContactDataBase
    var nid: Long = -1
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
    private val pickImage = 100
    private var imageUri: String? = null
    var catid: Long = 0

    private lateinit var catRepo: CategoryRepository
    var time24: String = ""
    var fullDate: String = ""

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_contact)

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
        binding = ActivityAddUpdateContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskType = intent.getStringExtra("Task").toString()

        category = intent.getStringExtra("category").toString()
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

        catViewModel.allCatData.observe(this) {

            it.forEach {
                arrayCatergory.add(it.catTitle.toString())
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
            }

        }

        binding.takeImage.setOnClickListener {
            pickProfilePictureTask()
        }

        spinner.onItemSelectedListener = getCategory()

        if (taskType.equals("Edit")) {
            val name = intent.getStringExtra("nameText")
            val phone = intent.getStringExtra("phoneText")
            val stime = intent.getStringExtra("time")
            var sdate = intent.getStringExtra("date")
            val scategory = intent.getStringExtra("category")
            val wdate = intent.getStringExtra("time24")
            val nId = intent.getLongExtra("noteId", -1)
            val catsid = intent.getLongExtra("catId", -1)
            val fdate = Date(intent.getLongExtra("fdate",calendar.timeInMillis))
            var uriImg = intent.getStringExtra("uriImage")

            Log.e("******", fdate.toString())
//        datef = fdate
//        calendar.time= fdate
             imageUri= uriImg

            binding.image.visibility=View.VISIBLE

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
            if (catsid != null) {
                catid = catsid
            }
            nid = nId

            binding.titleField.setText(name)
            binding.descField.setText(phone)
            binding.setDateBtn.setText(fullDate)
            binding.setTimeBtn.setText(selectedTime)
            binding.button.setText("Update")


        } else {
            binding.button.setText("ADD")
        }

        binding.button.setOnClickListener {
            if (binding.titleField.text.isNotEmpty() && binding.descField.text.isNotEmpty() && selectedTime.isNotEmpty()) {
                scheduleNotification()
                if (taskType.equals("Edit")) {
                    viewModel.editContactlist(
                        catid,
                        binding.titleField.text.toString(),
                        fullDate,
                        selectedTime,
                        binding.descField.text.toString(),
                        calendar.time,
                        imageUri.toString(),
                        nid
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
                            catid,
                            imageUri.toString()
                        )
                    )
                    this.finish()
                }
            } else {
                binding.titleField.error = "name is required"
                binding.descField.error = "Must enter phone number"

            }
        }


    }

    fun getTimeBefore10minutes(currentDate: String?): String? {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm")
        try {
            // Get calendar set to current date and time with Singapore time zone
            calendar.time = format.parse(currentDate)

            //Set calendar before 10 minutes
            calendar.add(Calendar.MINUTE, -10)
            //Formatter
            val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"))
            return formatter.format(calendar.time)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return null
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, NotificationClass::class.java)
        val title = binding.titleField.text.toString()
        val message = binding.descField.text.toString()
        intent.putExtra("title_Extra", title)
        intent.putExtra("desc_Extra", message)

        val cal = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE,calendar.get(Calendar.MINUTE))
            set(Calendar.SECOND, 0)
            add(Calendar.MINUTE, -10)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            Random.nextInt(),
            intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            cal.timeInMillis,
            pendingIntent
        )

    }

    private fun createNotificationChannel() {
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
            if (arrayList[i].catTitle.equals(category)) {
                scolor = catDataList[i].catColor.toString()
            }

        }
    }

    fun getId(arrayList: List<Catergory>, cat: String) {

        for (i in arrayList.indices) {
            if (arrayList[i].catTitle.equals(cat)) {
                catid = catDataList[i].categoryId
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
                getId(catDataList, category)
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

    private fun pickProfilePictureTask() {
        var intent: Intent? = null
        if (Build.VERSION.SDK_INT > 19) {
            intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
        } else {
            intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
        intent.type = "image/*"
        startActivityForResult(intent, pickImage)
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == pickImage) {
//            imageUri = data?.data
//            println("imageUri")
//            println(imageUri)
//            binding.uploadImageView.setImageURI(imageUri)
//            binding.uploadImageView.visibility=View.VISIBLE
//        }
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == RESULT_OK) {
            if (data != null) {
                /**Getting bitmap
                 *
                 * InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                 * Bitmap userPicBitmap = BitmapFactory.decodeStream(inputStream);
                 */
                val file = File(getFileNameFromURI(data.data!!))
                   imageUri = data.data.toString() + ""
                 binding.uploadImageView.setImageURI(data.data)
                 binding.uploadImageView.visibility=View.VISIBLE
                getContentResolver().takePersistableUriPermission(data.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            }
        }
    }


    private fun getFileNameFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path

        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)
            result = cursor.getString(idx)
            cursor.close()
        }
        println(">>>>>>>>>>>>>>>$result")
        return result
    }
}






