package com.example.jetpackcomponentsapp.util

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.room.CustomEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

public object ConvertList {

    private val TAG : String = ConvertList::class.java.getSimpleName()

    private fun toListModel(customEntity : List<CustomEntity>) : List<CustomHolderModel> {
        val itemList : MutableList<CustomHolderModel> = mutableListOf<CustomHolderModel>()
        customEntity.map {
            itemList.add(
                CustomHolderModel(it.id?:0, it.name?:"")
            )
        }
        return itemList
    }

    private fun toPagingDataModel(pagingDatum : PagingData<CustomEntity>) : PagingData<CustomHolderModel> {
        return pagingDatum.map {
            CustomHolderModel(it.id?:0, it.name?:"")
        }
    }

    suspend fun toFlowListModel(localList : Flow<List<CustomEntity>>, scope : CoroutineScope) : Flow<List<CustomHolderModel>> {
        return localList.map { entityList ->
            toListModel(entityList)
        }
    }

    suspend fun toSharedFlowListModel(localList : Flow<List<CustomEntity>>, scope : CoroutineScope) : SharedFlow<List<CustomHolderModel>> {
        return localList.map { entityList ->
            toListModel(entityList)
        }.shareIn(scope = scope, SharingStarted.Lazily)
    }

    suspend fun toStateFlowListModel(localList : Flow<List<CustomEntity>>, scope : CoroutineScope) : StateFlow<List<CustomHolderModel>> {
        return localList.map { entityList ->
            toListModel(entityList)
        }.stateIn(scope = scope/*, SharingStarted.Eagerly, initialValue = false*/)
    }

    public fun toEntity(customModel: CustomHolderModel) : CustomEntity {
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

    public fun toSharedFlowPagingDataModel(flow : Flow<PagingData<CustomEntity>>, scope : CoroutineScope) : SharedFlow<PagingData<CustomHolderModel>> {
        return flow.map {
            toPagingDataModel(it)
        }.cachedIn(scope).shareIn(scope, SharingStarted.Lazily)
    }
}