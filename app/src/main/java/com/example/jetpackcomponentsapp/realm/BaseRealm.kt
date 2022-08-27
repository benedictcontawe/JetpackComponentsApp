package com.example.jetpackcomponentsapp.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

abstract class BaseRealm {
    private var mRealm : Realm

    constructor(realm : Realm) {
        mRealm = realm
    }

    protected suspend fun _insert(it : RealmObject) {
        mRealm.write {
            copyToRealm(it)
        }
    }

    protected fun _insertBlocking(it : RealmObject) {
        mRealm.writeBlocking {
            copyToRealm(it)
        }
    }

    protected suspend fun <T : RealmObject> _update(clazz : KClass<T>, query : String, vararg args : Any?, callback : (realm : Realm ,oldObject : T) -> Unit) {
        mRealm.query<T>(clazz, query, *args).first().find()?.let { oldObject ->
            callback(mRealm, oldObject)
        }
    }

    protected suspend fun <T : RealmObject> _replace(it : RealmObject, clazz : KClass<T>) {
        _delete(clazz)
        _insert(it)
    }

    protected fun <T : RealmObject> _replaceBlocking(it : RealmObject, clazz : KClass<T>) {
        _deleteBlocking(clazz)
        _insertBlocking(it)
    }

    protected suspend fun <T : RealmObject> _delete(clazz : KClass<T>) {
        mRealm.write {
            delete(
                this.query<T>(clazz)
            )
        }
    }

    protected suspend fun <T : RealmObject> _delete(clazz : KClass<T>, query : String, vararg args : Any?) {
        mRealm.write {
            delete(
                this.query<T>(clazz, query, *args).find()
            )
        }
    }

    protected fun <T : RealmObject> _deleteBlocking(clazz : KClass<T>) {
        mRealm.writeBlocking {
            delete(
                this.query<T>(clazz)
            )
        }
    }

    protected fun <T : RealmObject> _deleteBlocking(clazz : KClass<T>, query : String, vararg args : Any?) {
        mRealm.writeBlocking {
            delete(
                this.query<T>(clazz, query, *args).find()
            )
        }
    }

    protected fun <T : RealmObject> _getAll(clazz : KClass<T>) : RealmResults<T> {
        return mRealm.query<T>(clazz).find()
    }

    protected fun <T : RealmObject> _getFirst(clazz : KClass<T>, query : String, vararg args : Any?) : T? {
        return mRealm.query<T>(clazz, query, *args).first().find()
        //return mRealm.query<T>(clazz, query, *args).first().find()
    }

    protected fun <T : RealmObject> _getFirstFlow(clazz : KClass<T>) : Flow<ObjectChange<T>>? {
        return mRealm.query<T>(clazz).first().find()?.asFlow()
    }

    protected suspend fun <T : RealmObject> _getAllFlow(clazz : KClass<T>) : Flow<ResultsChange<T>> {
        return mRealm.query<T>(clazz).find().asFlow()
    }
}