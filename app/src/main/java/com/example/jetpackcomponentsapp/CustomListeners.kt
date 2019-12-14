package com.example.jetpackcomponentsapp

interface CustomListeners {

    fun onUpdate(item : CustomModel, position: Int)

    fun onDelete(item : CustomModel, position: Int)

}
