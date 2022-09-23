package com.example.jetpackcomponentsapp.view.listeners

import com.example.jetpackcomponentsapp.model.CustomModel

public interface CustomListener {

    public fun onUpdate(item : CustomModel?, position : Int)

    public fun onDelete(item : CustomModel?, position : Int)
}
