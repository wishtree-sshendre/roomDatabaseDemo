package com.example.basicroomdatabase.data

import android.content.Context
import androidx.room.*

@Database(entities = [Contact::class], version = 1)
abstract class ContactDataBase:RoomDatabase() {
    abstract fun contactDao():ContactDao

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