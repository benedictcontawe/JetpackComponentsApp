package com.example.jetpackcomponentsapp

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ContactViewHolder : BaseContactViewHolder {

    companion object {
        private val TAG = ContactViewHolder::class.java.simpleName
    }
    //region contact_cell_default
    private val contactCell : ConstraintLayout
    private val contactImage : ImageView
    private val contactName : TextView
    private val contactEdit : ImageView
    private val contactDelete : ImageView
    //endregion
    constructor (context : Context, contactListener : ContactListener, itemView : View) : super(context, contactListener, itemView) {
        Log.d(TAG, "constructor")
        //region contact_cell_default
        contactCell = itemView.findViewById(R.id.constraint_layout)
        contactImage = itemView.findViewById(R.id.avatar)
        contactName = itemView.findViewById(R.id.name)
        contactEdit = itemView.findViewById(R.id.edit)
        contactDelete = itemView.findViewById(R.id.delete)
        //endregion
    }

    override fun bindDataToViewHolder(item : ContactViewHolderModel, position : Int) {
        Log.d(TAG, "bindDataToViewHolder($item, $position)")
        setPhoto(contactImage, item.photo)
        contactName.setText(item.name)
        contactEdit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                getContactListener().onClickItemEdit(item, position)
            }
        })
        contactDelete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                getContactListener().onClickItemDelete(item, position)
            }
        })
    }
}