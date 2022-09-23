package com.example.jetpackcomponentsapp.view.holders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.view.listeners.CustomListener
import com.example.jetpackcomponentsapp.model.CustomModel

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    private val customListener : CustomListener

    constructor(customListener : CustomListener, view : View) : super(view) {
        this.customListener = customListener
    }

    protected fun getContext() : Context {
        return itemView.getContext()
    }

    protected fun getListener() : CustomListener {
        return customListener
    }

    abstract fun bindDataToViewHolder(model : CustomModel, position : Int)
}