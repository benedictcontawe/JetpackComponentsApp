package com.example.jetpackcomponentsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.databinding.NasaCellBinder

class NasaAdapter : RecyclerView.Adapter<NasaViewHolder> {

    companion object {
        private val TAG = NasaAdapter::class.java.getSimpleName()
    }

    private val list : MutableList<NasaHolderModel>

    constructor() {
        list = mutableListOf<NasaHolderModel>()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : NasaViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val binder : NasaCellBinder = DataBindingUtil.inflate(layoutInflater, R.layout.cell, parent, false)
        return NasaViewHolder(binder)
    }

    override fun onBindViewHolder(holder : NasaViewHolder, position : Int) {
        holder.bindDataToViewHolder(list.get(position), position)
    }

    override fun getItemViewType(position : Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    public fun setItems(list : List<NasaHolderModel>?) {
        this.list.clear()
        this.list.addAll(list ?: emptyList<NasaHolderModel>())
        notifyDataSetChanged()
    }
}