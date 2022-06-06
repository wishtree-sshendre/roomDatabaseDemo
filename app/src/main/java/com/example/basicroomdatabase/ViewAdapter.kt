package com.example.basicroomdatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.basicroomdatabase.data.Contact

class ViewAdapter(private val context: Context, private val listener: MainActivity) :
    RecyclerView.Adapter<ViewAdapter.ContactViewHolder>() {
    var allContacts = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)

        val viewHolder = ContactViewHolder(inflater.inflate(R.layout.list_item, parent, false))
        viewHolder.deleteText.setOnClickListener {
            listener.onItemClicked(allContacts[viewHolder.adapterPosition])
        }
        viewHolder.edit_btn.setOnClickListener {
            listener.onclicked(allContacts[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.nameText.text = allContacts[holder.adapterPosition].name
        holder.phoneNoText.text = allContacts[holder.adapterPosition].phoneNo

    }

    override fun getItemCount(): Int {
        return allContacts.size
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nameText = view.findViewById<TextView>(R.id.name)
        val phoneNoText = view.findViewById<TextView>(R.id.phoneNo)
        val deleteText = view.findViewById<ImageView>(R.id.delete)
        val edit_btn = view.findViewById<ImageView>(R.id.edit)


    }

    fun updateContact(contactsList: List<Contact>) {
        allContacts.clear()
        allContacts.addAll(contactsList)

        notifyDataSetChanged()
    }

}




