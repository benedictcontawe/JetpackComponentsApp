package com.example.jetpackcomponentsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.example.jetpackcomponentsapp.CustomListener
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.CellBinder
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.util.CustomDiffUtilItemCallback

public class CustomPagingDataAdapter : PagingDataAdapter/*LoadStateAdapter*/<CustomHolderModel, CustomViewHolder> {

    companion object {
        private val TAG = CustomPagingDataAdapter::class.java.getSimpleName()
    }

    private val listener : CustomListener

    constructor(listener : CustomListener) : super(CustomDiffUtilItemCallback) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CustomViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val binder : CellBinder = DataBindingUtil.inflate(layoutInflater,
            R.layout.cell, parent, false)
        return CustomViewHolder(binder, listener)
    }

    override fun onBindViewHolder(holder : CustomViewHolder, position : Int) {
        holder.bindDataToViewHolder(
            getItem(position),
            position
        )
    }

    override fun getItemCount() : Int {
        return super.getItemCount()
    }

    public fun onRefresh() {
        this.refresh()
    }

    public fun onRetry() {
        this.retry()
    }
}