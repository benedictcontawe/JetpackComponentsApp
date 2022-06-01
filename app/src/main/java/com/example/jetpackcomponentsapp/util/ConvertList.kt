package com.example.jetpackcomponentsapp.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class ConvertList {

    companion object {
        private fun toListModel(customEntity : List<CustomEntity>) : List<CustomModel> {
            val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
            customEntity.map {
                itemList.add(
                    CustomModel(it.id?:0, it.name?:"")
                )
            }
            return itemList
        }

        fun toLiveDataListModel(localList : LiveData<List<CustomEntity>>) : LiveData<List<CustomModel>> {
            return Transformations.map<
                    List<CustomEntity>, //localList Data Type
                    List<CustomModel> //toListModel List Data Type
                    >(
                localList,
                Companion::toListModel
            )
        }

        suspend fun toStateFlowListModel(localList : Flow<List<CustomEntity>>, scope : CoroutineScope) : StateFlow<List<CustomModel>> {
            return localList.mapLatest { entityList ->
                toListModel(entityList)
            }.stateIn(scope = scope/*, SharingStarted.Eagerly, initialValue = false*/)
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