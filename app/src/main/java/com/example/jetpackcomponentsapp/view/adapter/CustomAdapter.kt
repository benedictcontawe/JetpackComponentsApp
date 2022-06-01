package com.example.jetpackcomponentsapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.CustomBinder
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.view.holder.CustomViewHolder

class CustomAdapter : RecyclerView.Adapter<CustomViewHolder> {

    /**Main */
    private lateinit var context : Context
    private lateinit var customListeners : CustomListeners
    private lateinit var customBinder : CustomBinder

    private var list : MutableList<CustomModel> = mutableListOf()

    constructor(context : Context, customListeners : CustomListeners) : super() {
        this.context = context
        this.customListeners = customListeners
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        customBinder = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_sample,
                parent,
                false
        )
        return CustomViewHolder(context, customListeners, customBinder)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        customBinder.customModel = list.get(position)
        holder.bindDataToViewHolder(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(items : List<CustomModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}