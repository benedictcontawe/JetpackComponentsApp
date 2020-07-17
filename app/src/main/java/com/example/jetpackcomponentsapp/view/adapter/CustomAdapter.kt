package com.example.jetpackcomponentsapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.CustomBinder
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.CustomDiffUtilItemCallback
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.view.holder.CustomViewHolder

class CustomAdapter : PagedListAdapter<CustomModel, CustomViewHolder> {

    /**Main */
    private lateinit var context : Context
    private lateinit var customListeners : CustomListeners

    private lateinit var customBinder : CustomBinder

    constructor(context : Context, customListeners : CustomListeners) : super(CustomDiffUtilItemCallback) {
        this.context = context
        this.customListeners = customListeners
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CustomViewHolder {
        customBinder = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_sample, parent, false
        )
        return CustomViewHolder(context, customListeners, customBinder)
    }

    override fun onBindViewHolder(holder : CustomViewHolder, position : Int) {
        customBinder.customModel = getItem(position)
        holder.bindDataToViewHolder(customBinder.customModel,position)
    }

    override fun getItemId(position : Int) : Long {
        return getItem(position)?.id?.toLong()?: RecyclerView.NO_ID
    }
}