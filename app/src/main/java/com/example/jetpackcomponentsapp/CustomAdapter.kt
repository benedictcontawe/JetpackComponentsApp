package com.example.jetpackcomponentsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.databinding.CustomBinder

class CustomAdapter : RecyclerView.Adapter<CustomViewHolder> {

    /**Main */
    private lateinit var context : Context
    private lateinit var customListeners : CustomListeners

    private lateinit var customBinder : CustomBinder

    private lateinit var list : MutableList<CustomModel>

    constructor(context : Context, customListeners : CustomListeners) : super() {
        this.context = context
        this.customListeners = customListeners
    }

    init {
        list = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        customBinder = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_sample,
                parent,
                false
        )
        return CustomViewHolder(context,customListeners,customBinder)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        customBinder.customModel = list.get(position)
        holder.bindDataToViewHolder(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(items : MutableList<CustomModel>) {
        list.clear()
        list.addAll(items)
        //notifyDataSetChanged()
        notifyItemRangeChanged(0, itemCount)
    }
}