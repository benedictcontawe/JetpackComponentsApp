package com.example.jetpackcomponentsapp.realm

interface IBaseData <T> {

    fun insert(t : T)

    fun update(t : T)

    fun delete(t : T)

    fun deleteAll()

    fun getAll() : List<T>

    fun getFirst() : T?

    fun queryAll(fieldName : String, value : String) : List<T>

    fun queryFirst(fieldName : String, value : String) : T
}