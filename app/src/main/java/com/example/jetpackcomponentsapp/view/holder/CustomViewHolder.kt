package com.example.jetpackcomponentsapp.view.holder

import android.content.Context
import android.view.View
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.databinding.CustomBinder

class CustomViewHolder : BaseViewHolder {

    private val customBinder : CustomBinder

    constructor(context: Context, customListeners: CustomListeners, customBinder : CustomBinder) : super(context, customListeners, customBinder.root) {
        this.customBinder = customBinder
    }

    override fun bindDataToViewHolder(item : CustomModel?, position : Int) {
        customBinder.executePendingBindings()
        setId(item?.id?:0)
        //customBinder.imageView.setBackgroundResource(item.icon?:0)
        customBinder.imageView.setImageResource(item?.icon?:0)
        customBinder.textView.setText(item?.name)
        customBinder.buttonEdit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                item?.let { getListener().onUpdate(it,position) }
            }
        })
        customBinder.buttonDelete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                item?.let { getListener().onDelete(it,position) }
            }
        })
    }
}