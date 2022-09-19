package com.example.jetpackcomponentsapp.view.holder

import android.view.View
import com.example.jetpackcomponentsapp.view.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.databinding.CustomBinder

class CustomViewHolder : BaseViewHolder, View.OnClickListener {

    private val binder : CustomBinder

    constructor(customListeners : CustomListeners, binder : CustomBinder) : super(customListeners, binder.root) {
        this.binder = binder
    }

    override fun bindDataToViewHolder(model : CustomModel, position : Int) {
        binder.setHolder(model)
        binder.setPosition(position)
        binder.executePendingBindings()
        binder.textView.setText(model.name)
        binder.imageView.setImageResource(model?.icon ?: 0) //binder.imageView.setBackgroundResource(item.icon ?: 0)
        binder.imageView.setImageResource(model.icon?:0)
        binder.buttonEdit.setOnClickListener(this@CustomViewHolder)
        binder.buttonDelete.setOnClickListener(this@CustomViewHolder)
    }

    override fun onClick(view : View?) {
        if (view == binder.buttonEdit) {
            getListener().onUpdate(binder.getHolder(), binder.getPosition())
        } else if(view == binder.buttonDelete) {
            getListener().onDelete(binder.getHolder(), binder.getPosition())
        }
    }
}