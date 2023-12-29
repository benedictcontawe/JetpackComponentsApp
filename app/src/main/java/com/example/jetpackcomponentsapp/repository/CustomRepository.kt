package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.model.CustomModel

public class CustomRepository() : BaseRepository {


    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        public fun getInstance() : CustomRepository {
            return INSTANCE ?: CustomRepository()
        }
    }

    init {

    }

    private fun getItem(id : Int) : CustomModel {
        return CustomModel(id,id.toString())
    }

    override public fun getItems() : List<CustomModel> {
        return listOf<CustomModel>(getItem(0), getItem(1),getItem(2),getItem(3),getItem(4),getItem(5),getItem(6),getItem(7),getItem(8),getItem(9),getItem(10))
    }
}