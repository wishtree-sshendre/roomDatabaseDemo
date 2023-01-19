package com.example.basicroomdatabase

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.basicroomdatabase.data.Contact
import com.example.basicroomdatabase.data.JoinClass

class ViewAdapter(private val context: Context, private val listener: MainActivity) :
    RecyclerView.Adapter<ViewAdapter.ContactViewHolder>() {
    var allContacts = ArrayList<JoinClass>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)

        val viewHolder = ContactViewHolder(inflater.inflate(R.layout.list_item, parent, false))
        viewHolder.deleteText.setOnClickListener {
            listener.onItemClicked(allContacts[viewHolder.adapterPosition].noteId)

        }
        viewHolder.edit_btn.setOnClickListener {
            listener.onclicked(allContacts[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.nameText.text = allContacts[holder.adapterPosition].noteTitle
        holder.phoneNoText.text = allContacts[holder.adapterPosition].nDesc
        holder.iv.setImageURI(Uri.parse(allContacts[holder.adapterPosition].image))
        holder.dateText.text = allContacts[holder.adapterPosition].date
        holder.timeText.text = allContacts[holder.adapterPosition].time
        holder.catText.text ="Category: ${allContacts[holder.adapterPosition].catTitle}"

        holder.card_view.setCardBackgroundColor(Color.parseColor(allContacts[holder.adapterPosition].catColor))
    }

    override fun getItemCount(): Int {
        return allContacts.size
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nameText = view.findViewById<TextView>(R.id.name)
        val phoneNoText = view.findViewById<TextView>(R.id.phoneNo)
        val dateText=view.findViewById<TextView>(R.id.dateView)
        val timeText=view.findViewById<TextView>(R.id.timeView)
        val deleteText = view.findViewById<ImageView>(R.id.delete)
        val edit_btn = view.findViewById<ImageView>(R.id.edit)
        val card_view =view.findViewById<CardView>(R.id.card_view)
        val catText =view.findViewById<TextView>(R.id.cat_tv)
        val iv =view.findViewById<ImageView>(R.id.iv)

    }

    fun updateContact(contactsList: List<JoinClass>) {
        allContacts.clear()
        allContacts.addAll(contactsList)
        notifyDataSetChanged()
    }

}








