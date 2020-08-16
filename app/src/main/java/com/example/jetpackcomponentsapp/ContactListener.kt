package com.example.jetpackcomponentsapp

interface ContactListener {

    fun onClickItemEdit(item : ContactViewHolderModel, position : Int)
    fun onClickItemDelete(item : ContactViewHolderModel, position : Int)
}