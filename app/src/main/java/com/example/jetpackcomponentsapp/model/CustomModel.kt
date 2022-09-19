package com.example.jetpackcomponentsapp.model

import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.room.CustomEntity
import com.example.jetpackcomponentsapp.util.Constants

data class CustomModel (
    val id : Int,
    val name : String,
    val icon : Int,
) {
    companion object {
        private val TAG = CustomModel::class.java.getSimpleName()
    }
    constructor() : this(id = Constants.NEGATIVE_ONE, name = Constants.BLANK, icon = R.drawable.ic_android_black) {

    }
    constructor(entity : CustomEntity) : this(id = entity.id ?: Constants.NEGATIVE_ONE, name = entity.name ?: Constants.BLANK, icon = entity.icon ?: Constants.ZERO) {

    }
    constructor(name : String) : this(id = Constants.NEGATIVE_ONE, name = name, icon = R.drawable.ic_launcher_foreground) {

    }
    constructor(id : Int, name : String) : this(id = id, name = name, icon = R.drawable.ic_launcher_foreground) {

    }
    constructor(id : Int?, name : String, icon : Int?) : this(id = id ?: Constants.NEGATIVE_ONE, name = name, icon = icon ?: R.drawable.ic_launcher_foreground) {

    }
    override fun toString() : String {
        return "$TAG($id, $name, $icon)" ?: super.toString()
    }
}