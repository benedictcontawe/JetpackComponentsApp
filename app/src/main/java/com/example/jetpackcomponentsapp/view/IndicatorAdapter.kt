package com.example.jetpackcomponentsapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.R

class IndicatorAdapter : RecyclerView.Adapter<IndicatorViewHolder> {

    companion object {
        private val TAG = IndicatorAdapter::class.java.getSimpleName()
        private const val SelectedIndicator = 0
        private const val UnSelectedLargeIndicator = 1
        private const val UnSelectedMediumIndicator = 2
        private const val UnSelectedSmallIndicator = 3
    }

    private var size : Int
    private var selected : Int

    constructor() {
        size = 0
        selected = 0
    }

    constructor(size : Int?) {
        this.size = size ?: 0
        selected = 0
    }

    constructor(size : Int?, selected : Int?) {
        this.size = size ?: 0
        this.selected = selected ?: 0
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : IndicatorViewHolder {
        Log.d(TAG, "onCreateViewHolder($parent,$viewType)")
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View = if (viewType == IndicatorAdapter.SelectedIndicator) layoutInflater.inflate(R.layout.cell_circle_selected, parent, false)
        else if (viewType == IndicatorAdapter.UnSelectedLargeIndicator) layoutInflater.inflate(R.layout.cell_circle_large, parent, false)
        else if (viewType == IndicatorAdapter.UnSelectedMediumIndicator) layoutInflater.inflate(R.layout.cell_circle_medium, parent, false)
        else if (viewType == IndicatorAdapter.UnSelectedSmallIndicator) layoutInflater.inflate(R.layout.cell_circle_small, parent, false)
        else layoutInflater.inflate(R.layout.cell_circle_large, parent, false)
        return IndicatorViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder : IndicatorViewHolder, position : Int) {
        holder.bindDataToViewHolder()
    }

    override fun getItemCount() : Int {
        return this.size
    }

    override fun getItemViewType(position : Int) : Int {
        return if (position == selected) IndicatorAdapter.SelectedIndicator
        else if (true) IndicatorAdapter.UnSelectedLargeIndicator
        else if (true) IndicatorAdapter.UnSelectedMediumIndicator
        else if (true) IndicatorAdapter.UnSelectedSmallIndicator
        else super.getItemViewType(position)
    }

    public fun setSelected(selected : Int) {
        notifyItemChanged(this.selected)
        this.selected = selected
        notifyItemChanged(selected)
    }
}