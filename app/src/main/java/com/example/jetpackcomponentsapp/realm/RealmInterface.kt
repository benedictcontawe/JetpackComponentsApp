package com.example.jetpackcomponentsapp.realm

import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow

interface RealmInterface <T : RealmObject> {

    public suspend fun insert(t : T)

    public suspend fun insert(t : RealmList<T>)

    public suspend fun update(t : T)

    public fun insertBlocking(t : T)

    public fun insertBlocking(t : RealmList<T>)

    public suspend fun replace(t : T)

    public suspend fun replace(t : RealmList<T>)

    public fun replaceBlocking(t : T)

    public fun replaceBlocking(t : RealmList<T>)

    public suspend fun delete()

    public suspend fun delete(t : T)

    public suspend fun delete(t : RealmList<T>)

    public fun deleteBlocking(t : T)

    public fun deleteBlocking(t : RealmList<T>)

    public fun getAll() : RealmResults<T>

    public fun getFirst(t : T) : T?

    public suspend fun getAllFlow() : Flow<ResultsChange<T>>
}