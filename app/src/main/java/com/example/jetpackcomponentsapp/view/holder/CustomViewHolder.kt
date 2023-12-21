package com.example.jetpackcomponentsapp.view.holder

import android.view.View
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.databinding.CustomBinder

class CustomViewHolder : BaseViewHolder {

    private var customBinder : CustomBinder

    constructor(customListeners : CustomListeners, customBinder : CustomBinder) : super(customListeners, customBinder.root) {
        this.customBinder = customBinder
    }

    override fun bindDataToViewHolder(model : CustomModel, position : Int) {
        customBinder.setCustomModel(model)
        customBinder.executePendingBindings()
        //customBinder.imageView.setBackgroundResource(item.icon ?: 0)
        customBinder.imageView.setImageResource(model.icon)
        customBinder.buttonEdit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                getListener().onUpdate(model ,position)
            }
        })
        customBinder.buttonDelete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                getListener().onDelete(model, position)
            }
        })
    }
}