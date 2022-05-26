package com.example.basicroomdatabase

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.basicroomdatabase.data.Contact

class ListAdapter (private val context: Activity, private val arrayList: List<Contact>): ArrayAdapter<Contact>(context,R.layout.list_item,arrayList){
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val inflator: LayoutInflater = LayoutInflater.from(context)

            val view: View = inflator.inflate(R.layout.list_item,null)
            val idText = view.findViewById<TextView>(R.id.cid)
            val nameText = view.findViewById<TextView>(R.id.name)
            val phoneNoText= view.findViewById<TextView>(R.id.phoneNo)
            nameText.text = "Title : "+arrayList[position].name
            phoneNoText.text= "Body : "+arrayList[position].phoneNo
            idText.text= "Id : "+arrayList[position].cid.toString()

            return view
        }
    }
