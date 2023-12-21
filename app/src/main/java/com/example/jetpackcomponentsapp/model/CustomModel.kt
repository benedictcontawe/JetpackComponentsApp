package com.example.jetpackcomponentsapp.model

import com.example.jetpackcomponentsapp.R

public class CustomModel {

    companion object {
        private val TAG = CustomModel::class.java.getSimpleName()
    }

    val id : Int?
    var name : String? = null
    val icon : Int

    constructor(name : String) {
        this.id = null
        this.name = name
        this.icon = R.drawable.ic_android_black
    }

    constructor(id : Int, name : String) {
        this.id = id
        this.name = name
        this.icon = R.drawable.ic_android_black
    }

    constructor(id : Int?, name : String, icon : Int?) {
        this.id = id
        this.name = name
        this.icon = icon ?: R.drawable.ic_android_black
    }

    override fun toString(): String {
        return "$TAG($id, $name, $icon)" ?: super.toString()
    }
}