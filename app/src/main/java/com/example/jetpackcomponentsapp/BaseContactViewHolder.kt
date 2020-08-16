package com.example.jetpackcomponentsapp

import android.content.Context
import android.util.Log
import android.view.View

abstract class BaseContactViewHolder : BaseViewHolder {

    companion object {
        private val TAG = BaseContactViewHolder::class.java.simpleName
    }

    private val contactListener : ContactListener

    constructor(context : Context, contactListener : ContactListener, itemView : View) : super(context, itemView) {
        Log.d(TAG, "constructor")
        this.contactListener = contactListener
    }

    public fun getContactListener() : ContactListener = contactListener

    abstract fun bindDataToViewHolder(item : ContactViewHolderModel, position : Int)
}