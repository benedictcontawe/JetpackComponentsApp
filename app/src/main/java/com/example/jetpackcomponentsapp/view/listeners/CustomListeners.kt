package com.example.jetpackcomponentsapp.view.listeners

import com.example.jetpackcomponentsapp.model.CustomModel

interface CustomListeners {

    fun onUpdate(model : CustomModel?, position : Int)

    fun onDelete(model : CustomModel?, position : Int)

}
