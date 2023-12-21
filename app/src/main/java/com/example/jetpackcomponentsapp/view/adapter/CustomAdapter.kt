package com.example.jetpackcomponentsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.CustomBinder
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.view.holder.CustomViewHolder

public class CustomAdapter : RecyclerView.Adapter<CustomViewHolder> {

    /**Main */
    private val customListeners : CustomListeners

    private lateinit var customBinder : CustomBinder

    private var list : MutableList<CustomModel> = mutableListOf()

    constructor(customListeners : CustomListeners) : super() {
        this.customListeners = customListeners
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CustomViewHolder {
        customBinder = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_sample,
            parent,
            false
        )
        return CustomViewHolder(customListeners, customBinder)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        customBinder.customModel = list.get(position)
        holder.bindDataToViewHolder(list[position],position)
    }

    override fun getItemId(position : Int) : Long {
        //return super.getItemId(position)
        return list[position].id?.toLong()?:RecyclerView.NO_ID
    }

    override fun getItemCount(): Int {
        return list.size
    }

    public fun setItems(items : List<CustomModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}