package com.example.basicroomdatabase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FireBaseReference {
    var database2: DatabaseReference = FirebaseDatabase.getInstance().getReference("Contacts")
    fun insertData(){
    }

}