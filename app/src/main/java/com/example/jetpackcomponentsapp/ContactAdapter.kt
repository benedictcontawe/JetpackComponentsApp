package com.example.jetpackcomponentsapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter : RecyclerView.Adapter<BaseContactViewHolder> {

    companion object {
        private val TAG = ContactAdapter::class.java.simpleName
        public const val HeaderView = 0 //contact_cell_header
        public const val DefaultView = 1 //contact_cell_default
    }

    private val list : MutableList<ContactViewHolderModel> = mutableListOf()

    private lateinit var contactListener : ContactListener

    constructor(contactListener : ContactListener) : super() {
        Log.d(TAG,"constructor")
        this.contactListener = contactListener
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseContactViewHolder {
        Log.d(TAG, "onCreateViewHolder($parent,$viewType)")
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View
        return when(viewType) {
            HeaderView -> {
                view = layoutInflater.inflate(R.layout.contact_cell_header, parent, false)
                HeaderViewHolder(parent.context, contactListener, view)
            }
            DefaultView -> {
                view = layoutInflater.inflate(R.layout.contact_cell_default, parent, false)
                ContactViewHolder(parent.context, contactListener, view)
            }
            else -> {
                view = layoutInflater.inflate(R.layout.contact_cell_default, parent, false)
                ContactViewHolder(parent.context, contactListener, view)
            }
        }
    }

    override fun onBindViewHolder(holder : BaseContactViewHolder, position : Int) {
        Log.d(TAG, "onBindViewHolder($holder, $position)")
        if (list[position].photo.isNotBlank()) {
            holder.setIsRecyclable(false)
        } else {
            holder.setIsRecyclable(true)
        }
        holder.bindDataToViewHolder(list[position], position)
    }

    override fun getItemId(position : Int) : Long {
        return list[position].id
    }

    override fun getItemCount() : Int {
        return list.size
    }

    public fun getContactCount() : Int {
        Log.d(TAG, "getContactCount() : ${list.filter { it.viewType == DefaultView }.size}")
        return list.filter { it.viewType == DefaultView }.size
    }

    override fun getItemViewType(position : Int) : Int {
        Log.d(TAG, "getItemViewType($position) : ${list[position].viewType}")
        return list[position].viewType
    }

    public fun setItems(contacts : List<ContactViewHolderModel>) {
        list.clear()
        list.addAll(contacts)
        notifyDataSetChanged()
    }
}