package com.example.jetpackcomponentsapp.model

import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.room.CustomEntity

data class CustomHolderModel (
    val id : Int?,
    val name : String?,
    val icon : Int?,
) {
    companion object {
        private val TAG = CustomHolderModel::class.java.getSimpleName()
    }
    constructor() : this(id = null, name = null, icon = R.drawable.ic_android_black) {

    }
    constructor(entity : CustomEntity) : this(id = entity.id, name = entity.name, icon = entity.icon) {

    }
    constructor(name : String) : this(id = null, name = name, icon = R.drawable.ic_launcher_foreground) {

    }
    constructor(id : Int, name : String) : this(id = id, name = name, icon = R.drawable.ic_launcher_foreground) {

    }
    override fun toString() : String {
        return "$TAG($id, $name, $icon)" ?: super.toString()
    }
}