package com.example.jetpackcomponentsapp.view

import android.content.Context
import android.util.Log
import android.view.View
import com.example.jetpackcomponentsapp.model.CustomModel

abstract class BaseViewPagerHolder : BaseViewHolder {

    companion object {
        private val TAG = BaseViewPagerHolder::class.java.getSimpleName()
    }

    constructor(context : Context, itemView : View) : super(context, itemView) {
        Log.d(TAG, "constructor")
    }

    abstract fun bindDataToViewHolder(customModel : CustomModel, position : Int)
}