package com.example.jetpackcomponentsapp.view.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    private val customListeners : CustomListeners

    constructor(customListeners : CustomListeners, view : View) : super(view) {
        this.customListeners = customListeners
    }

    protected fun getContext() : Context {
        return itemView.getContext()
    }

    protected fun getListener() : CustomListeners {
        return customListeners
    }

    abstract fun bindDataToViewHolder(model : CustomModel, position : Int)
}