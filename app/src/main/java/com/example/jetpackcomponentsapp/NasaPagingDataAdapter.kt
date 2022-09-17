package com.example.jetpackcomponentsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.example.jetpackcomponentsapp.databinding.NasaCellBinder

public class NasaPagingDataAdapter : PagingDataAdapter<NasaHolderModel, NasaViewHolder> {

    companion object {
        private val TAG = NasaPagingDataAdapter::class.java.getSimpleName()
    }


    constructor() : super(NasaDiffUtilItemCallback) {

    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : NasaViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val binder : NasaCellBinder = DataBindingUtil.inflate(layoutInflater, R.layout.cell, parent, false)
        return NasaViewHolder(binder)
    }

    override fun onBindViewHolder(holder : NasaViewHolder, position : Int) {
        holder.bindDataToViewHolder(
            getItem(position),
            position
        )
    }

    override fun getItemCount() : Int {
        return super.getItemCount()
    }
}