package com.example.jetpackcomponentsapp.model

data class CustomEntity (
    var name : String? = null,

    var icon : Int? = null
) {
    var id : Int? = null

    constructor(id : Int, name : String, icon : Int) : this() {
        this.id = id
        this.name = name
        this.icon = icon
    }

    override fun toString(): String {
        return "MessageThreadListEntity(Id=$id, Name=$name, Icon=$icon)"
    }
}