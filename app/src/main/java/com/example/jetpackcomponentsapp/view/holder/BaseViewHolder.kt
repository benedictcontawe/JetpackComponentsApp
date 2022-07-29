package com.example.jetpackcomponentsapp.view.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    /**Main */
    private val customListeners : CustomListeners
    /**Data */
    private var id : Int? = null

    constructor(context : Context, customListeners : CustomListeners, view : View) : super(view) {
        this.customListeners = customListeners
    }

    fun setId(id : Int) {
        this.id
    }

    fun getId() : Int{
        return id ?: 0
    }

    fun getListener() : CustomListeners {
        return customListeners
    }

    abstract fun bindDataToViewHolder(item : CustomModel, position : Int)
}