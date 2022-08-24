package com.example.jetpackcomponentsapp.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
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

    protected suspend fun <T : RealmObject> replace(it : RealmObject, clazz : KClass<T>) {
        deleteAll(clazz)
        insert(it)
    }

    suspend fun delete(clazz : Class<out RealmObject>, columnName : String, value : String) {
        /*mRealm.executeTransaction({ realm ->
            val query = realm.where(clazz).equalTo(columnName, value).findAll()
            query.deleteAllFromRealm()
        })
        mRealm.write {
            this.query(clazz)
            delete(
                this.query<CustomObject>("id = $0 AND name = $1", clazz.id, customObject.name).find()
            )
        }*/
    }

    protected suspend fun delete(model : RealmObject) {
        /*mRealm.executeTransaction({ realm ->
            model.deleteFromRealm()
            realm.commitTransaction()
        })*/
    }

    protected suspend fun insertAll(it : Collection<RealmObject>, clazz : Class<out RealmObject>) {
        /*mRealm.executeTransaction({ realm ->
            //realm.delete(clazz)
            realm.insert(it)
        })*/
    }
    /*
    protected suspend fun <T> getAll(clazz : Class<out RealmObject>) : List<T> {
        val items = mRealm.where(clazz).findAll()
        return mRealm.copyFromRealm(items) as List<T>
    }
    */
    /*
    protected suspend fun <T> getFirst(clazz : Class<out RealmObject>) : T? {
        mRealm.query<CustomObject>("id = $0", id)?.first()?.find()
        val user = mRealm.where(clazz).findFirst()

        if(user != null) {
            var result = mRealm.copyFromRealm(user)
            return (result as T?)
        }

        return null
    }
    */
    protected suspend fun <T : RealmObject> deleteAll(clazz : KClass<T>) {
        mRealm.write {
            delete(
                this.query<T>(clazz)
            )
        }
    }
    /*
    protected suspend fun createObject(clazz: Class<out RealmObject>): RealmObject {
        return mRealm.createObject(clazz)
    }
    */
    /*
    protected suspend fun <T> query(clazz: Class<out RealmObject>, fieldName: String, value: String) : List<T> {
        val items = mRealm.where(clazz).equalTo(fieldName, value).findAll()
        return mRealm.copyFromRealm(items) as List<T>
    }
    */
    /*
    protected suspend fun <T> queryFirst(clazz: Class<out RealmObject>, fieldName: String, value: String) : T {
        val items = mRealm.where(clazz).equalTo(fieldName, value).findFirst()
        return mRealm.copyFromRealm(items) as T
    }
    */
}