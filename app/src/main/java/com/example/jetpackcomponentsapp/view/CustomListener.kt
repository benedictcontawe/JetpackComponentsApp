package com.example.jetpackcomponentsapp.view

import com.example.jetpackcomponentsapp.model.CustomModel

interface CustomListener {

    public fun onUpdate(item : CustomModel?, position : Int)

    public fun onDelete(item : CustomModel?, position : Int)

}
