package com.example.jetpackcomponentsapp.model

import com.example.jetpackcomponentsapp.R

class CustomModel {

    var id: Int?
    var name: String?
    var icon: Int?

    constructor(name : String) {
        this.id = null
        this.name = name
        this.icon = R.drawable.ic_android_black
    }

    constructor(id : Int?, name : String) {
        this.id = id
        this.name = name
        this.icon = R.drawable.ic_android_black
    }

    constructor(id : Int, name : String, icon : Int) {
        this.id = id
        this.name = name
        this.icon = icon
    }
}
