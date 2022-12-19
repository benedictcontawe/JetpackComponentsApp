package com.example.jetpackcomponentsapp

import android.util.Log
import com.example.jetpackcomponentsapp.databinding.ContactHeaderBinder

class HeaderViewHolder : BaseContactViewHolder {

    companion object {
        private val TAG = HeaderViewHolder::class.java.getSimpleName()
    }

    private val binder : ContactHeaderBinder

    constructor (listener : ContactListener, binder : ContactHeaderBinder) : super(listener, binder.getRoot()) {
        Log.d(TAG, "constructor")
        this.binder = binder
    }

    override fun bindDataToViewHolder(model : ContactViewHolderModel, position : Int) {
        Log.d(TAG,"bindDataToViewHolder($model,$position)")
        binder.setHolder(model)
        binder.setPosition(position)
        binder.executePendingBindings()
        binder.headerPlaceHolder.setText(model.name)
    }
}