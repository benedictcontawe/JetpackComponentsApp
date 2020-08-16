package com.example.jetpackcomponentsapp

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView

class HeaderViewHolder : BaseContactViewHolder {

    companion object {
        private val TAG = HeaderViewHolder::class.java.simpleName
    }
    //region contact_cell_header
    var contactHeader : TextView
    //endregion
    constructor (context : Context, contactListener : ContactListener, itemView : View) : super(context, contactListener, itemView) {
        Log.d(TAG, "constructor")
        //region contact_cell_header
        contactHeader = itemView.findViewById(R.id.headerPlaceHolder)
        //endregion
    }

    override fun bindDataToViewHolder(item : ContactViewHolderModel, position : Int) {
        Log.d(TAG,"bindDataToViewHolder($item,$position)")
        contactHeader.setText(item.name)
    }
}