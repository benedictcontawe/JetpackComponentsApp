package com.example.jetpackcomponentsapp.view.listeners

import com.example.jetpackcomponentsapp.model.CustomHolderModel

interface CustomListeners {

    fun onUpdate(model : CustomHolderModel?, position : Int)

    fun onDelete(model : CustomHolderModel?, position : Int)

}
