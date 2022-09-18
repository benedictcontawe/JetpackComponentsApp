package com.example.jetpackcomponentsapp

import com.example.jetpackcomponentsapp.model.CustomHolderModel

public interface CustomListener {

    fun onUpdate(item : CustomHolderModel?, position: Int)

    fun onDelete(item : CustomHolderModel?, position: Int)
}