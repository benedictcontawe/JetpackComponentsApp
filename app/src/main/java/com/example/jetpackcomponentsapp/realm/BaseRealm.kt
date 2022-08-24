package com.example.jetpackcomponentsapp.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

abstract class BaseRealm {
    private var mRealm : Realm

    constructor(realm : Realm) {
        mRealm = realm
    }

    protected suspend fun insert(it : RealmObject) {
        mRealm.write {
            copyToRealm(it)
        }
    }

    protected suspend fun insert(it : RealmList<RealmObject>) {
        it.forEach { _self ->
            insert(_self)
        }
    }

    protected fun insertBlocking(it : RealmObject) {
        mRealm.writeBlocking {
            copyToRealm(it)
        }
    }

    protected fun insertBlocking(it : RealmList<RealmObject>) {
        it.forEach { _self ->
            insertBlocking(_self)
        }
    }

    protected suspend fun <T : RealmObject> replace(it : RealmObject, clazz : KClass<T>) {
        delete(clazz)
        insert(it)
    }

    protected suspend fun <T : RealmObject> replace(it : RealmList<RealmObject>, clazz : KClass<T>) {
        delete(clazz)
        it.forEach { _self ->
            insert(_self)
        }
    }

    protected fun <T : RealmObject> replaceBlocking(it : RealmObject, clazz : KClass<T>) {
        deleteBlocking(clazz)
        insertBlocking(it)
    }

    protected fun <T : RealmObject> replaceBlocking(it : RealmList<RealmObject>, clazz : KClass<T>) {
        deleteBlocking(clazz)
        it.forEach { _self ->
            insertBlocking(_self)
        }
    }

    protected suspend fun <T : RealmObject> delete(clazz : KClass<T>) {
        mRealm.write {
            delete(
                this.query<T>(clazz)
            )
        }
    }

    protected suspend fun <T : RealmObject> delete(clazz : KClass<T>, query : String, vararg args : Any?) {
        mRealm.write {
            delete(
                this.query(clazz, query, args).find()
            )
        }
    }

    protected fun <T : RealmObject> deleteBlocking(clazz : KClass<T>) {
        mRealm.writeBlocking {
            delete(
                this.query<T>(clazz)
            )
        }
    }

    protected fun <T : RealmObject> deleteBlocking(clazz : KClass<T>, query : String, vararg args : Any?) {
        mRealm.writeBlocking {
            delete(
                this.query(clazz, query, args).find()
            )
        }
    }

    protected fun <T : RealmObject> getAll(clazz : KClass<T>) : RealmResults<T> {
        return mRealm.query<T>(clazz).find()
    }

    protected suspend fun <T : RealmObject> getFirst(clazz : KClass<T>, query : String, vararg args : Any?) : T? {
        return mRealm.query<T>(clazz, query, query).first().find()
    }

    protected suspend fun <T : RealmObject> getAllFlow(clazz : KClass<T>) : Flow<ResultsChange<T>> {
        return mRealm.query<T>(clazz).find().asFlow()
    }
}