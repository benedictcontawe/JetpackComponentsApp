package com.example.jetpackcomponentsapp.repository

public interface BaseRepository {

    suspend fun update(booleanKey : Boolean)

    suspend fun update(stringKey : String)

    suspend fun update(integerKey : Int)

    suspend fun update(doubleKey : Double)

    suspend fun update(longKey : Long)
}