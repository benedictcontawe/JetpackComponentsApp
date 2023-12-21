package com.example.jetpackcomponentsapp.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.room.CustomEntity

object ConvertList {

    private fun toListModel(customEntity : List<CustomEntity>) : MutableList<CustomModel> {
        val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
        customEntity.map {
            itemList.add(
                CustomModel(it.id?:0, it.name?:"")
            )
        }
        return itemList
    }

    fun toLiveDataListModel(localList : LiveData<List<CustomEntity>>) : LiveData<List<CustomModel>> {
        return localList.map { list : List<CustomEntity> -> toListModel(list) }
    }

    fun toEntity(customModel : CustomModel) : CustomEntity {
        return when(customModel.id) {
            null -> {
                CustomEntity (
                    customModel.name?:"",
                    customModel.icon?:0
                )
            }
            else -> {
                CustomEntity (
                    customModel.id,
                    customModel.name?:"",
                    customModel.icon?:0
                )
            }
        }
    }
}