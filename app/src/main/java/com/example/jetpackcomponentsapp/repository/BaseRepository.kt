package com.example.jetpackcomponentsapp.repository

public interface BaseRepository {

    public suspend fun update(booleanKey : Boolean)

    public suspend fun update(stringKey : String)

    public suspend fun update(integerKey : Int)

    public suspend fun update(doubleKey : Double)

    public suspend fun update(longKey : Long)
}