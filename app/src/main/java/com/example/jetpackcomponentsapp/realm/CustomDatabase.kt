package com.example.jetpackcomponentsapp.realm

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

object CustomDatabase {
    private var configuration : RealmConfiguration? = null
    private var realm : Realm? = null

    fun onOpen() {
        configuration = RealmConfiguration.Builder(schema = setOf(CustomObject::class))
            .schemaVersion(1L)
            .name("DataStore.realm").build()
        if (configuration != null) realm = Realm.open(configuration!!)
    }


    fun write(customObject : CustomObject) {
        realm?.writeBlocking {
            copyToRealm(customObject)
        }
    }

    suspend fun writeAsync(customObject : CustomObject) {
        Log.e("CustomDatabase", "writeAsync customObject ${customObject}")
        customObject.id = getMax()
        Log.e("CustomDatabase", "writeAsync customObject ${customObject}")
        realm?.write {
            copyToRealm(customObject)
        }
    }

    suspend fun writeAsync(customObjects : RealmList<CustomObject>) {
        customObjects.forEach { customObject ->
            writeAsync(customObject)
        }
    }

    private fun getMax() : Int {
        val results : RealmResults<CustomObject>? = realm?.query<CustomObject>()?.find()
        return if (results?.isEmpty() == true) 1
        else if (results?.filter { it.id != null }?.isNotEmpty() == true) results.maxOfOrNull { it.id!! }!!.plus(1)
        else -1
    }

    public fun query() : RealmResults<CustomObject>? {
        return realm?.query<CustomObject>()?.find()
    }

    public suspend fun queryFlow() : Flow<ResultsChange<CustomObject>>? {
        return realm?.query<CustomObject>()?.find()?.asFlow()
    }

    fun query(id : Int) : CustomObject? {
        return realm?.query<CustomObject>("id = $0", id)?.first()?.find()
    }

    fun query(name : String) : CustomObject? {
        return realm?.query<CustomObject>("name = $0", name)?.first()?.find()
    }

    fun query(customObject : CustomObject) : CustomObject? {
        return realm?.query<CustomObject>("id = $0 AND name = $1", customObject.id, customObject.name)?.first()?.find()
    }

    suspend fun queryAsync(id : Int) {
        realm?.query<CustomObject>("id = $0", id)?.asFlow()?.collect {
            results : ResultsChange<CustomObject> ->
            when (results) {
                is InitialResults<CustomObject> -> { println("Initial results size ${results.list.size}")}
                is UpdatedResults<CustomObject> -> {
                    println("Updated results changes ${results.changes} deletes ${results.deletions} insertions ${results.insertions}")
                }
                /*is DeletedList<CustomObject> -> {
                    println("Deleted results changes ${results.list}")
                }*/
            }
        }
    }

    suspend fun queryAsync(name : String) {
        realm?.query<CustomObject>("name = $0", name)?.asFlow()?.collect {
                results : ResultsChange<CustomObject> ->
            when (results) {
                is InitialResults<CustomObject> -> { println("Initial results size ${results.list.size}")}
                is UpdatedResults<CustomObject> -> {
                    println("Updated results changes ${results.changes} deletes ${results.deletions} insertions ${results.insertions}")
                }
            }
        }
    }

    suspend fun update(newObject : CustomObject) {
        realm?.query<CustomObject>("id = $0", newObject.id)
            ?.first()
            ?.find()
            ?.let { oldObject ->
                realm?.write {
                    this.findLatest(oldObject)?.name = newObject.name
                    this.findLatest(oldObject)?.icon = newObject.icon
                }
            }
    }

    suspend fun delete(customObject : CustomObject) {
        realm?.write {
            delete(
                this.query<CustomObject>("id = $0 AND name = $1", customObject.id, customObject.name).find()
            )
        }
    }

    suspend fun deleteAll() {
        realm?.write {
            delete(
                this.query<CustomObject>()
            )
        }
    }

    public fun onCLose() {
        if (realm?.isClosed() == false) realm?.close()
    }
}