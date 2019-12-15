package com.example.jetpackcomponentsapp.view.holder

import android.content.Context
import android.view.View
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.databinding.CustomBinder

class CustomViewHolder : BaseViewHolder {

    private lateinit var customBinder : CustomBinder

    constructor(context: Context, customListeners: CustomListeners, customBinder : CustomBinder) : super(context, customListeners, customBinder.root) {
        this.customBinder = customBinder
    }

    override fun bindDataToViewHolder(item: CustomModel, position: Int) {
        setId(item.id?:0)
        //customBinder.imageView.setBackgroundResource(item.icon?:0)
        customBinder.imageView.setImageResource(item.icon?:0)
        customBinder.buttonEdit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                getListener().onUpdate(item,position)
            }
        })
        customBinder.buttonDelete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                getListener().onDelete(item,position)
            }
        })
    }

}