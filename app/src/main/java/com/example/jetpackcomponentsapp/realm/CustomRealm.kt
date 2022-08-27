package com.example.jetpackcomponentsapp.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.flow.Flow

class CustomRealm : BaseRealm, RealmInterface<CustomObject> {

    constructor(realm : Realm) : super(realm) {

    }

    override suspend fun insert(t : CustomObject) {
        t.id = getMaxID()
        _insert(t)
    }

    override suspend fun insert(t : RealmList<CustomObject>) {
        t.forEach { _self ->
            _self.id = getMaxID()
            _insert(_self)
        }
    }

    override suspend fun update(t : CustomObject) {
        _update<CustomObject>(CustomObject::class, "id = $0", t.id, callback = { realm, oldObject ->
            realm.writeBlocking {
                this.findLatest(oldObject)?.name = t.name
                this.findLatest(oldObject)?.icon = t.icon
            }
        })
    }

    override fun insertBlocking(t : CustomObject) {
        t.id = getMaxID()
        _insertBlocking(t)
    }

    override fun insertBlocking(t : RealmList<CustomObject>) {
        t.forEach { _self ->
            _self.id = getMaxID()
            _insertBlocking(_self)
        }
    }

    override suspend fun replace(t : CustomObject) {
        t.id = getMaxID()
        _replace(t , CustomObject::class)
    }

    override suspend fun replace(t : RealmList<CustomObject>) {
        t.forEach { _self ->
            _self.id = getMaxID()
            _replace(_self , CustomObject::class)
        }
    }

    override fun replaceBlocking(t : CustomObject) {
        t.id = getMaxID()
        _replaceBlocking(t, CustomObject::class)
    }

    override fun replaceBlocking(t : RealmList<CustomObject>) {
        t.forEach { _self ->
            _self.id = getMaxID()
            _replaceBlocking(_self, CustomObject::class)
        }
    }

    override suspend fun delete() {
        _delete(CustomObject::class)
    }

    override suspend fun delete(t : CustomObject) {
        _delete(CustomObject::class, "id = $0", t.id)
        //_delete(CustomObject::class, "id = $0 AND name = $1", t.id, t.name)
    }

    override suspend fun delete(t : RealmList<CustomObject>) {
        t.forEach { _self ->
            _delete(CustomObject::class, "id = $0", _self.id)
        }
    }

    override fun deleteBlocking(t : CustomObject) {
        _deleteBlocking(CustomObject::class, "id = $0", t.id)
    }

    override fun deleteBlocking(t : RealmList<CustomObject>) {
        t.forEach { _self ->
            _deleteBlocking(CustomObject::class, "id = $0", _self.id)
        }
    }

    override fun getAll() : RealmResults<CustomObject> {
        return _getAll(CustomObject::class)
    }

    override fun getFirst(t : CustomObject) : CustomObject? {
        //return _getFirst<CustomObject>(CustomObject::class, "id = $0", t.id)
        return _getFirst<CustomObject>(CustomObject::class, "id = $0 OR name = $1 OR icon = $2", t.id, t.name, t.icon)
    }

    private fun getMaxID() : Int {
        val results : RealmResults<CustomObject>? = _getAll<CustomObject>(CustomObject::class)
        return if (results?.isEmpty() == true) 1
        else if (results?.filter { it.id != null }?.isNotEmpty() == true) results.maxOfOrNull { it.id!! }!!.plus(1)
        else -1
    }

    fun getFirstFlow() : Flow<ObjectChange<CustomObject>>? {
        return _getFirstFlow(CustomObject::class)
    }

    override suspend fun getAllFlow() : Flow<ResultsChange<CustomObject>> {
        return _getAllFlow<CustomObject>(CustomObject::class)
    }
}