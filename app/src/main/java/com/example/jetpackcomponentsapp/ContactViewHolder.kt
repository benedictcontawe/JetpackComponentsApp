package com.example.jetpackcomponentsapp

import android.util.Log
import android.view.View
import com.example.jetpackcomponentsapp.databinding.ContactCellBinder

class ContactViewHolder : BaseContactViewHolder {

    companion object {
        private val TAG = ContactViewHolder::class.java.getSimpleName()
    }

    private val binder : ContactCellBinder

    constructor (listener : ContactListener, binder : ContactCellBinder) : super(listener, binder.getRoot()) {
        Log.d(TAG, "constructor")
        this.binder = binder
    }

    override fun bindDataToViewHolder(model : ContactViewHolderModel, position : Int) {
        Log.d(TAG, "bindDataToViewHolder($model, $position)")
        binder.setHolder(model)
        binder.setPosition(position)
        binder.executePendingBindings()
        setPhoto(binder.avatar, model.photo)
        binder.name.setText(model.name)
        binder.edit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                getContactListener().onClickItemEdit(model, position)
            }
        })
        binder.delete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                getContactListener().onClickItemDelete(model, position)
            }
        })
    }
}