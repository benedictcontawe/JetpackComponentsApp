package com.example.jetpackcomponentsapp.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
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
                    customModel.name ?: "",
                    customModel.icon?:0
                )
            }
            else -> {
                CustomEntity (
                    customModel.id,
                    customModel.name ?: "",
                    customModel.icon?:0
                )
            }
        }
    }

    private fun toPagingDataModel(pagingDatum : PagingData<CustomEntity>) : PagingData<CustomModel> {
        return pagingDatum.map {
            CustomModel(it.id?:0, it.name?:"")
        }
    }

    public fun toSharedFlowPagingDataModel(flow : Flow<PagingData<CustomEntity>>, scope : CoroutineScope) : SharedFlow<PagingData<CustomModel>> {
        return flow.map {
            toPagingDataModel(it)
        }.cachedIn(scope).shareIn(scope, SharingStarted.Lazily)
    }

    public suspend fun toStateFlowPagingDataModel(flow : Flow<PagingData<CustomEntity>>, scope : CoroutineScope) : StateFlow<PagingData<CustomModel>> {
        return flow.map {
            toPagingDataModel(it)
        }.cachedIn(scope).stateIn(scope)
    }
}