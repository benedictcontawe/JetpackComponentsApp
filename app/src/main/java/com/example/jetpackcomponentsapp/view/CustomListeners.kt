package com.example.jetpackcomponentsapp.view

import com.example.jetpackcomponentsapp.model.CustomModel

interface CustomListeners {

    fun onUpdate(item : CustomModel, position: Int)

    fun onDelete(item : CustomModel, position: Int)

}
