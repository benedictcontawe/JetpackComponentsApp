package com.example.jetpackcomponentsapp.repository

import com.example.jetpackcomponentsapp.realm.CustomObject
import com.example.jetpackcomponentsapp.realm.CustomRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.notifications.ObjectChange
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
    private var customRealm : CustomRealm? = null

    init {
        configuration = RealmConfiguration.Builder(schema = setOf(CustomObject::class))
            .schemaVersion(1L)
            .name("DataStore.realm").build()
        if (configuration != null) realm = Realm.open(configuration!!)
        if (realm != null) customRealm = CustomRealm(realm!!)
    }
    //region CRUD Operation
    public fun insertBlocking(customObject : CustomObject) {
        customRealm?.insertBlocking(customObject)
    }

    override public suspend fun insert(customObject : CustomObject) {
        customRealm?.insert(customObject)
    }

    override public suspend fun insert(customObjects : RealmList<CustomObject>) {
        customObjects.forEach { customObject ->
            customRealm?.insert(customObject)
        }
    }

    override public suspend fun update(customObject : CustomObject) {
        customRealm?.update(customObject)
        /*realm?.query<CustomObject>("id = $0", customObject.id)
            ?.first()
            ?.find()
            ?.let { oldObject ->
                realm?.write {
                    this.findLatest(oldObject)?.name = customObject.name
                    this.findLatest(oldObject)?.icon = customObject.icon
                }
            }
        */
    }

    override public suspend fun delete(customObject : CustomObject) {
        customRealm?.delete(customObject)
    }

    override public suspend fun deleteAll() {
        customRealm?.delete()
    }

    override public fun getFirstOrNull(customObject : CustomObject) : CustomObject? {
        return customRealm?.getFirst(customObject)//?: realm?.query<CustomObject>("id = $0 AND name = $1", customObject.id, customObject.name)?.first()?.find()
    }

    override suspend fun getFirstFlow() : Flow<ObjectChange<CustomObject>>? {
        return customRealm?.getFirstFlow()
    }

    override public suspend fun getAll() : RealmResults<CustomObject>? {
        return customRealm?.getAll()
    }

    override public suspend fun getAllFlow() : Flow<ResultsChange<CustomObject>>? {
        return customRealm?.getAllFlow()
    }
    //endregion
    override public suspend fun onCLose() {
        if (realm?.isClosed() == false) realm?.close()
    }
}