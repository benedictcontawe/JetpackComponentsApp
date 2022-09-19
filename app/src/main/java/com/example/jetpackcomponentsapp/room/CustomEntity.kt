package com.example.jetpackcomponentsapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_table")
data class CustomEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id : Int?,

    @ColumnInfo(name = "Name")
    val name : String?,

    @ColumnInfo(name = "Icon")
    val icon : Int?
) {
    companion object {
        private val TAG = CustomEntity::class.java.getSimpleName()
    }
    constructor(name : String, icon : Int) : this(id = null, name = name, icon = icon) {

    }
    override fun toString() : String {
        return "$TAG(Id=$id, Name=$name, Icon=$icon)"
    }
}