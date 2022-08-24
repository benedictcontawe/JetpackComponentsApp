package com.example.jetpackcomponentsapp.realm

import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface RealmInterface <T> {

    public suspend fun insert(it : RealmObject)

    public suspend fun insert(it : RealmList<RealmObject>)

    public fun insertBlocking(it : RealmObject)

    public fun insertBlocking(it : RealmList<RealmObject>)

    public suspend fun <T : RealmObject> replace(it : RealmObject, clazz : KClass<T>)

    public suspend fun <T : RealmObject> replace(it : RealmList<RealmObject>, clazz : KClass<T>)

    public fun <T : RealmObject> replaceBlocking(it : RealmObject, clazz : KClass<T>)

    public fun <T : RealmObject> replaceBlocking(it : RealmList<RealmObject>, clazz : KClass<T>)

    public suspend fun <T : RealmObject> delete(clazz : KClass<T>)

    public suspend fun <T : RealmObject> delete(clazz : KClass<T>, query : String, vararg args : Any?)

    public fun <T : RealmObject> deleteBlocking(clazz : KClass<T>)

    public fun <T : RealmObject> deleteBlocking(clazz : KClass<T>, query : String, vararg args : Any?)

    public fun <T : RealmObject> getAll(clazz : KClass<T>) : RealmResults<T>

    public suspend fun <T : RealmObject> getFirst(clazz : KClass<T>, query : String, vararg args : Any?) : T?

    public suspend fun <T : RealmObject> getAllFlow(clazz : KClass<T>) : Flow<ResultsChange<T>>
}