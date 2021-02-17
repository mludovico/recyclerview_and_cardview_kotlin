package com.example.recyclerviewandcardview

import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_item.*
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter(var listener: IClickContactItemListener): RecyclerView.Adapter<ContactAdapter.ContacAdapterViewHolder>() {

    private var list: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContacAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContacAdapterViewHolder(view, list, listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContacAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(list: ArrayList<Contact>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class ContacAdapterViewHolder(itemView: View, var list: List<Contact>, var listener: IClickContactItemListener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.clickContactItem(list[adapterPosition])
            }
        }
        fun bind(contact: Contact) {
            itemView.nameText.text = contact.name
            itemView.phoneText.text = contact.phone
        }
    }
}