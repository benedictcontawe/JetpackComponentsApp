package com.example.jetpackcomponentsapp.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

object ConvertList {

    private val TAG = ConvertList::class.java.getSimpleName()

    private fun toListModel(customEntity : List<CustomEntity>) : List<CustomModel> {
        val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
        customEntity.map {
            itemList.add(
                CustomModel(it.id?:0, it.name?:"")
            )
        }
        return itemList
    }

    suspend fun toFlowListModel(localList : Flow<List<CustomEntity>>, scope : CoroutineScope) : Flow<List<CustomModel>> {
        return localList.map { entityList ->
            toListModel(entityList)
        }
    }

    suspend fun toSharedFlowListModel(localList : Flow<List<CustomEntity>>, scope : CoroutineScope) : SharedFlow<List<CustomModel>> {
        return localList.map { entityList ->
            toListModel(entityList)
        }.shareIn(scope = scope, SharingStarted.Lazily)
    }

    suspend fun toStateFlowListModel(localList : Flow<List<CustomEntity>>, scope : CoroutineScope) : StateFlow<List<CustomModel>> {
        return localList.map { entityList ->
            toListModel(entityList)
        }.stateIn(scope = scope/*, SharingStarted.Eagerly, initialValue = false*/)
    }

    public fun toEntity(customModel : CustomModel) : CustomEntity {
        return when(customModel.id) {
            Constants.NEGATIVE_ONE -> {
                CustomEntity(
                    null,
                    customModel.name ?:"",
                    customModel.icon ?:0
                )
            }
            else -> {
                CustomEntity(
                    customModel.id,
                    customModel.name ?:"",
                    customModel.icon ?:0
                )
            }
        }
    }
}