package com.example.basicroomdatabase.data

import android.content.Context
import androidx.room.*
import com.example.basicroomdatabase.MainActivity

@Database(entities = [Contact::class], version = 1)
abstract class ContactDataBase:RoomDatabase() {
    abstract fun contactDao():ContactDao

    companion object{
        @Volatile
        private var Instance: ContactDataBase? =null

        fun getDatabase(context: MainActivity):ContactDataBase{
            if (Instance== null)
            {
                Instance = Room.databaseBuilder(context.applicationContext,ContactDataBase::class.java,"contactDB").build()
            }
            return Instance!!
        }

    }
}