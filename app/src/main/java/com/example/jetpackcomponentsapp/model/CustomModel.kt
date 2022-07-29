package com.example.jetpackcomponentsapp.model

import com.example.jetpackcomponentsapp.R

data class CustomModel (
    val id : Int? = null,
    var name : String? = null,
    val icon : Int? = null
) {
    constructor() : this(id = null, name = null, icon = R.drawable.ic_android_black) {

    }

    constructor(name : String) : this(id = null, name = name, icon = R.drawable.ic_android_black) {

    }

    constructor(id : Int, name : String) : this(id = id, name = name, icon = R.drawable.ic_android_black) {

    }

    override fun toString() : String {
        return "CustomModel(Id=$id, Name=$name, Icon=$icon)" ?: super.toString()
    }
}