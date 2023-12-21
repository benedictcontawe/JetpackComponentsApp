package com.example.jetpackcomponentsapp.view.holder

import android.view.View
import com.example.jetpackcomponentsapp.databinding.CellBinder
import com.example.jetpackcomponentsapp.view.listeners.CustomListeners
import com.example.jetpackcomponentsapp.model.CustomHolderModel

class CustomViewHolder : BaseViewHolder, View.OnClickListener {

    companion object {
        private val TAG : String = CustomViewHolder.javaClass::class.java.getSimpleName()
    }

    private val binder : CellBinder

    constructor(binder: CellBinder, customListener : CustomListeners) : super(binder.root, customListener) {
        this.binder = binder
    }

    override fun bindDataToViewHolder(model : CustomHolderModel?, position : Int) {
        binder.setHolder(model)
        binder.setPosition(position)
        binder.executePendingBindings()
        binder.textView.setText(model?.name)
        binder.imageView.setImageResource(model?.icon ?: 0) //binder.imageView.setBackgroundResource(item.icon ?: 0)
        binder.imageView.setImageResource(model?.icon?:0)
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