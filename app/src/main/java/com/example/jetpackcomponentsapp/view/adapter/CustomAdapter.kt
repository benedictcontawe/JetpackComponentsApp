package com.example.jetpackcomponentsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.CellBinder
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.view.listeners.CustomListeners
import com.example.jetpackcomponentsapp.view.holder.CustomViewHolder

class CustomAdapter : RecyclerView.Adapter<CustomViewHolder> {

    companion object {
        private val TAG = CustomAdapter::class.java.getSimpleName()
    }

    private val customListener : CustomListeners
    private val list : MutableList<CustomHolderModel>

    constructor(customListener : CustomListeners) : super() {
        this.customListener = customListener
        this.list = mutableListOf()
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CustomViewHolder {
        val binder : CellBinder = DataBindingUtil.inflate (
            LayoutInflater.from(parent.context),
            R.layout.cell_sample,
            parent,
            false
        )
        return CustomViewHolder(binder, customListener)
    }

    override fun onBindViewHolder(holder : CustomViewHolder, position : Int) {
        holder.bindDataToViewHolder(
            list.get(position),
            position
        )
    }

    override fun getItemId(position : Int) : Long {
        return list.get(position).id?.toLong() ?: super.getItemId(position)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    fun setItems(items : List<CustomHolderModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}