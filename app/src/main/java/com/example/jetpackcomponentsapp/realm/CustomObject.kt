package com.example.jetpackcomponentsapp.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CustomObject : RealmObject {
    @PrimaryKey
    var id : Int? = null
    var name : String? = null
    var icon : Int? = null

   constructor()

    constructor(name : String, icon : Int) {
        //this.id = UUID.randomUUID().toString().toInt()
        this.name = name
        this.icon = icon
    }

    constructor(id : Int, name : String, icon : Int) {
        this.id = id
        this.name = name
        this.icon = icon
    }

    override fun toString(): String {
        return "CustomObject($id, $name, $icon)" ?: super.toString()
    }
}