package com.example.jetpackcomponentsapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.databinding.ContactCellBinder
import com.example.jetpackcomponentsapp.databinding.ContactHeaderBinder

class ContactAdapter : RecyclerView.Adapter<BaseContactViewHolder> {

    companion object {
        private val TAG = ContactAdapter::class.java.getSimpleName()
    }

    private val list : MutableList<ContactViewHolderModel> = mutableListOf()
    private val contactListener : ContactListener

    constructor(contactListener : ContactListener) : super() {
        Log.d(TAG,"constructor")
        this.contactListener = contactListener
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseContactViewHolder {
        Log.d(TAG, "onCreateViewHolder($parent,$viewType)")
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        return when(viewType) {
            ContactModel.HeaderView -> {
                val binder : ContactHeaderBinder = DataBindingUtil.inflate(layoutInflater, R.layout.cell_contact_header, parent, false)
                HeaderViewHolder(contactListener, binder)
            }
            ContactModel.CellView -> {
                val binder : ContactCellBinder = DataBindingUtil.inflate(layoutInflater, R.layout.cell_contact_default, parent, false)
                ContactViewHolder(contactListener, binder)
            }
            else -> {
                val binder : ContactCellBinder = DataBindingUtil.inflate(layoutInflater, R.layout.cell_contact_default, parent, false)
                ContactViewHolder(contactListener, binder)
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
        Log.d(TAG, "getContactCount() : ${list.filter { it.viewType == ContactModel.CellView }.size}")
        return list.filter { it.viewType == ContactModel.CellView }.size
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