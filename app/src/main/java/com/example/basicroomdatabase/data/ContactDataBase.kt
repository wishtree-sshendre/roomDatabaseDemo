package com.example.basicroomdatabase.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import com.example.basicroomdatabase.MainActivity

@Database(entities = [Contact::class,Catergory::class], version = 2)
@TypeConverters(Convertor::class)
abstract class ContactDataBase:RoomDatabase() {
    abstract fun contactDao():ContactDao
    abstract fun categoryDao():CategoryDao
    companion object{
        @Volatile
        private var Instance: ContactDataBase? =null

        fun getDatabase(context: Context):ContactDataBase{
            if (Instance== null)
            {
                Instance = Room.databaseBuilder(context.applicationContext,ContactDataBase::class.java,"contactDB").build()
            }
            return Instance!!
        }

    }
}