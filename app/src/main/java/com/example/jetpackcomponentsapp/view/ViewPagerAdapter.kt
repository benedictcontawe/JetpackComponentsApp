package com.example.jetpackcomponentsapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.model.CustomModel

public class ViewPagerAdapter : RecyclerView.Adapter<BaseViewPagerHolder> {

    companion object {
        private val TAG = ViewPagerAdapter::class.java.getSimpleName()
    }

    private val list : MutableList<CustomModel> = mutableListOf<CustomModel>()

    constructor() {

    }

    constructor(list : List<CustomModel>?) {
        setItems(list ?: listOf<CustomModel>())
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseViewPagerHolder {
        Log.d(TAG, "onCreateViewHolder($parent,$viewType)")
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View = layoutInflater.inflate(R.layout.cell_custom, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder : BaseViewPagerHolder, position : Int) {
        holder.bindDataToViewHolder(list.get(position), position)
    }

    override fun getItemCount() : Int {
        return this.list.size
    }

    public fun setItems(list : List<CustomModel>) {
        this.list.clear()
        this.list.addAll(list)
    }
}