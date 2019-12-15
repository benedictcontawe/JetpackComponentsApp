package com.example.jetpackcomponentsapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_table")
class CustomEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private var id : Int? = null

    @ColumnInfo(name = "Name")
    private var name : String? = null

    @ColumnInfo(name = "Icon")
    private var icon : Int? = null

    constructor(name : String,icon : Int) {
        this.name = name
        this.icon = icon
    }

    fun getId() : Int? = this.id

    fun getName() : String? {
        return this.name
    }

    fun getIcon() : Int? = this.icon

}