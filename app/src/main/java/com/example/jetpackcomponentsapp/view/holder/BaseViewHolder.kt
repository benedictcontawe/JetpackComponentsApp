package com.example.jetpackcomponentsapp.view.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.view.listeners.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomHolderModel

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    private val customListeners : CustomListeners

    constructor(view : View, customListeners : CustomListeners) : super(view) {
        this.customListeners = customListeners
    }

    protected fun getContext() : Context {
        return itemView.getContext()
    }

    protected fun getListener() : CustomListeners {
        return customListeners
    }

    abstract fun bindDataToViewHolder(model : CustomHolderModel?, position : Int)
}