package com.example.jetpackcomponentsapp.util

import android.util.Log
import androidx.lifecycle.Lifecycle
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.realm.CustomObject
import io.realm.kotlin.notifications.DeletedList
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

object ConvertList {

    private val TAG : String = ConvertList::class.java.getSimpleName()

    private fun toListModel(customEntity : RealmResults<CustomObject>) : List<CustomModel> {
        val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
        customEntity.forEach {
            itemList.add(
                CustomModel(it.id?:0, it.name?:"", it.icon)
            )
        }
        return itemList
    }

    suspend fun toSharedFlowListModel(localList : Flow<ResultsChange<CustomObject>>, scope : CoroutineScope) : SharedFlow<List<CustomModel>> {
        return localList.map { objectList : ResultsChange<CustomObject> ->
            when (objectList) {
                is InitialResults<CustomObject> -> { Log.e(TAG,"InitialResults list ${objectList.list}") }
                is UpdatedResults<CustomObject> -> {
                    Log.e(TAG,"UpdatedResults list ${objectList.list} changes ${objectList.changes} deletes ${objectList.deletions} insertions ${objectList.insertions}")
                } is DeletedList<*> -> {
                Log.e(TAG,"DeletedList")
            }
            }
            toListModel(objectList.list)
        }.shareIn(scope = scope, SharingStarted.Lazily)
    }

    suspend fun toStateFlowListModel(localList : Flow<ResultsChange<CustomObject>>, scope : CoroutineScope) : StateFlow<List<CustomModel>> {
        return localList.map { objectList : ResultsChange<CustomObject> ->
            when (objectList) {
                is InitialResults<CustomObject> -> { Log.e(TAG,"InitialResults list ${objectList.list}") }
                is UpdatedResults<CustomObject> -> {
                    Log.e(TAG,"UpdatedResults list ${objectList.list} changes ${objectList.changes} deletes ${objectList.deletions} insertions ${objectList.insertions}")
                } is DeletedList<*> -> { Log.e(TAG,"DeletedList") }
            }
            toListModel(objectList.list)
        }.stateIn(scope = scope, /*SharingStarted.Lazily, Lifecycle.State.STARTED ,initialValue = false*/)
    }

    suspend fun toStateFlowListModel(localList : Flow<ResultsChange<CustomObject>>, callback : (list : List<CustomModel>) -> Unit) {
        localList.collect { objectList : ResultsChange<CustomObject> ->
            callback(
                toListModel(
                    objectList.list
                )
            )
        }
    }

    fun toObject(customModel : CustomModel) : CustomObject {
        return when(customModel.id) {
            null -> {
                CustomObject(
                    customModel.name?:"",
                    customModel.icon?:0
                )
            }
            else -> {
                CustomObject(
                    customModel.id,
                    customModel.name?:"",
                    customModel.icon?:0
                )
            }
        }
    }
}