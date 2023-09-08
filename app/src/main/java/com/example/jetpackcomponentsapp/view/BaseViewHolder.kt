package com.example.jetpackcomponentsapp.view

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    companion object {
        private val TAG = BaseViewPagerHolder::class.java.getSimpleName()
    }


    constructor(itemView : View) : super(itemView) {
        Log.d(TAG, "constructor")
    }

    protected fun getContext() : Context {
        return itemView.getContext()
    }
}