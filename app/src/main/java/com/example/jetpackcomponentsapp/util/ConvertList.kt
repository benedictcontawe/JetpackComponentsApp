package com.example.jetpackcomponentsapp.util

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.room.CustomEntity

class ConvertList {

    companion object {
        @Deprecated("Not Working")
        private fun toPagedListModel(customEntity : PagedList<CustomEntity>) : PagedList<CustomModel> {
            val itemList : MutableList<CustomModel> = mutableListOf()
            //customEntity.snapshot().toList().filter { it != null && it.id != null }.map {

            customEntity.snapshot().toList().map {
                itemList.add(
                        CustomModel(it?.id, it?.name?:"")
                )
            }

            return PagedList.Builder(ListDataSource(itemList), customEntity.config)
                    .setNotifyExecutor(UiThreadExecutor())
                    .setFetchExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    .build()
        }

        fun toListModel(customEntity: List<CustomEntity>) : MutableList<CustomModel> {
            val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
            customEntity.map {
                itemList.add(
                        CustomModel(it.id?:0, it.name?:"")
                )
            }
            itemList.sortBy { it.id }
            itemList.distinct()
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
        @Deprecated("Not Working")
        fun toLiveDataPagedListModel(localList : LiveData<PagedList<CustomEntity>>) : LiveData<PagedList<CustomModel>> {
            return Transformations.map<
                    PagedList<CustomEntity>, //localList Data Type
                    PagedList<CustomModel> //toPagedListModel List Data Type
                    >(
                    localList,
                    Companion::toPagedListModel
            )
        }

        fun toEntity(customModel: CustomModel) : CustomEntity {
            return when(customModel.id) {
                null -> {
                    CustomEntity(
                            0,
                            customModel.name?:"",
                            customModel.icon?:0
                    )
                }
                else -> {
                    CustomEntity(
                            customModel.id?:0,
                            customModel.name?:"",
                            customModel.icon?:0
                    )
                }
            }
        }
    }
}