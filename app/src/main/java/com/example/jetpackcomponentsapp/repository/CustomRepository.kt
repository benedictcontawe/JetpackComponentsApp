package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.realm.CustomObject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.flow.Flow

class CustomRepository : BaseRepository {

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance() : CustomRepository {
            return INSTANCE ?: CustomRepository()
        }
    }

    private var configuration : RealmConfiguration? = null
    private var realm : Realm? = null

    init {
        configuration = RealmConfiguration.Builder(schema = setOf(CustomObject::class))
            .schemaVersion(1L)
            .name("DataStore.realm").build()
        if (configuration != null) realm = Realm.open(configuration!!)
    }
    //region CRUD Operation
    public fun insertBlocking(customObject : CustomObject) {
        customObject.id = getMax()
        realm?.writeBlocking {
            copyToRealm(customObject)
        }
    }

    override public suspend fun insert(customObject : CustomObject) {
        customObject.id = getMax()
        realm?.write {
            copyToRealm(customObject)
        }
    }

    override public suspend fun insert(customObjects : RealmList<CustomObject>) {
        customObjects.forEach { customObject ->
            insert(customObject)
        }
    }

    override public suspend fun update(customObject : CustomObject) {
        realm?.query<CustomObject>("id = $0", customObject.id)
            ?.first()
            ?.find()
            ?.let { oldObject ->
                realm?.write {
                    this.findLatest(oldObject)?.name = customObject.name
                    this.findLatest(oldObject)?.icon = customObject.icon
                }
            }
    }

    override public suspend fun delete(customObject: CustomObject) {
        realm?.write {
            delete(
                this.query<CustomObject>("id = $0 AND name = $1", customObject.id, customObject.name).find()
            )
        }
    }

    override public suspend fun deleteAll() {
        realm?.write {
            delete(
                this.query<CustomObject>()
            )
        }
    }

    override public fun getFirstOrNull(id : Int) : CustomObject? {
        return realm?.query<CustomObject>("id = $0", id)?.first()?.find()
    }

    override public fun getFirstOrNull(name : String) : CustomObject? {
        return realm?.query<CustomObject>("name = $0", name)?.first()?.find()
    }

    override public fun getFirstOrNull(customObject : CustomObject) : CustomObject? {
        return realm?.query<CustomObject>("id = $0 AND name = $1", customObject.id, customObject.name)?.first()?.find()
    }

    override public suspend fun getFirstOrNullFlow(id : Int) : Flow<ResultsChange<CustomObject>>? {
        return realm?.query<CustomObject>("id = $0", id)?.asFlow()
    }

    override public suspend fun getFirstOrNullFlow(name : String) : Flow<ResultsChange<CustomObject>>? {
        return realm?.query<CustomObject>("name = $0", name)?.asFlow()
    }

    override public suspend fun getAll() : RealmResults<CustomObject>? {
        return realm?.query<CustomObject>()?.find()
    }

    override public suspend fun getAllFlow() : Flow<ResultsChange<CustomObject>>? {
        return realm?.query<CustomObject>()?.find()?.asFlow()
    }
    //endregion
    override public fun getMax() : Int {
        val results : RealmResults<CustomObject>? = realm?.query<CustomObject>()?.find()
        return if (results?.isEmpty() == true) 1
        else if (results?.filter { it.id != null }?.isNotEmpty() == true) results.maxOfOrNull { it.id!! }!!.plus(1)
        else -1
    }

    override public suspend fun onCLose() {
        if (realm?.isClosed() == false) realm?.close()
    }
}