package com.example.jetpackcomponentsapp

class CustomModel {

    var id: Int? = null
    var name: String? = null

    var icon: Int? = null

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name
        this.icon = R.drawable.ic_launcher_foreground
    }
}
