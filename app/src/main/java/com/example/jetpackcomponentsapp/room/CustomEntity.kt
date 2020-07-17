package com.example.jetpackcomponentsapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_table")
data class CustomEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id : Int,

    @ColumnInfo(name = "Name")
    var name : String? = null,

    @ColumnInfo(name = "Icon")
    var icon : Int? = null
) {

//    constructor(id : Int, name : String, icon : Int) : this() {
//        this.id = id
//        this.name = name
//        this.icon = icon
//    }

    override fun toString() : String {
        return "MessageThreadListEntity(Id=$id, Name=$name, Icon=$icon)"
    }
}