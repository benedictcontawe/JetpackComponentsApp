package com.example.jetpackcomponentsapp

import android.util.Log
import android.view.View

abstract class BaseContactViewHolder : BaseViewHolder {

    companion object {
        private val TAG = BaseContactViewHolder::class.java.simpleName
    }

    private val listener : ContactListener

    constructor(listener : ContactListener, itemView : View) : super(itemView) {
        Log.d(TAG, "constructor")
        this.listener = listener
    }

    public fun getContactListener() : ContactListener = listener

    abstract fun bindDataToViewHolder(model : ContactViewHolderModel, position : Int)
}