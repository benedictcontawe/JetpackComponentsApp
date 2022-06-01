package com.example.jetpackcomponentsapp.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asFlow
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.flow.*

class ConvertList {

    companion object {
        private fun toListModel(customEntity: List<CustomEntity>) : MutableList<CustomModel> {
            val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
            customEntity.map {
                itemList.add(
                        CustomModel(it.id?:0, it.name?:"")
                )
            }
            return itemList
        }

        fun toLiveDataListModel(localList : LiveData<List<CustomEntity>>) : LiveData<MutableList<CustomModel>> {
            return Transformations.map<
                    List<CustomEntity>, //localList Data Type
                    MutableList<CustomModel> //toListModel List Data Type
                    >(
                    localList,
                    Companion::toListModel
            )
        }

        suspend fun toStateFlowListModel(localList : StateFlow<List<CustomEntity>>) : StateFlow<MutableList<CustomModel>> {
            val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
            //val liveList : MutableSharedFlow<MutableList<CustomModel>> = MutableSharedFlow()
            localList.collect{ list ->
                list.map { item ->
                    itemList.add(
                        CustomModel(item.id ?: 0, item.name ?: "")
                    )
                }
            }
            //liveList.emit(itemList)
            //return liveList
        }

        fun toEntity(customModel: CustomModel) : CustomEntity {
            return when(customModel.id) {
                null -> {
                    CustomEntity(
                            customModel.name?:"",
                            customModel.icon?:0
                    )
                }
                else -> {
                    CustomEntity(
                            customModel.id!!,
                            customModel.name?:"",
                            customModel.icon?:0
                    )
                }
            }
        }
    }
}