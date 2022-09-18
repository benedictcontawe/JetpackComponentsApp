package com.example.jetpackcomponentsapp.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomponentsapp.CustomListener
import com.example.jetpackcomponentsapp.databinding.CellBinder
import com.example.jetpackcomponentsapp.model.CustomHolderModel

public class CustomViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

    companion object {
        private val TAG = CustomViewHolder::class.java.getSimpleName()
    }

    private val binder : CellBinder
    private val listener : CustomListener

    constructor(binder : CellBinder, listener : CustomListener) : super(binder.root) {
        this.binder = binder
        this.listener = listener
    }

    public fun bindDataToViewHolder(model : CustomHolderModel?, position : Int) {
        binder.setHolder(model)
        binder.setPosition(position)
        binder.executePendingBindings()
        binder.imageView.setImageResource(model?.icon ?: 0) //binder.imageView.setBackgroundResource(item.icon?:0)
        binder.textView.setText(model?.name)
        binder.buttonEdit.setOnClickListener(this@CustomViewHolder)
        binder.buttonDelete.setOnClickListener(this@CustomViewHolder)
    }

    override public fun onClick(view : View?) {
        if (view == binder.buttonEdit) {
            listener.onUpdate(binder.getHolder(), binder.getPosition())
        } else if(view == binder.buttonDelete) {
            listener.onDelete(binder.getHolder(), binder.getPosition())
        }
    }
}