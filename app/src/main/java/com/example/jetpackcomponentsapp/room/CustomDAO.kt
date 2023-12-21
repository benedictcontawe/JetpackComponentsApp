package com.example.jetpackcomponentsapp.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomDAO {
    //@Insert
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(customEntity : CustomEntity)

    @Update
    suspend fun update(customEntity : CustomEntity)

    //@Delete
    //fun delete(customEntity: CustomEntity)

    @Query("DELETE FROM custom_table WHERE Id = :id")
    suspend fun delete(id : Int?)

    @Query("DELETE FROM custom_table")
    suspend fun deleteAll()

    //@Query("SELECT * FROM custom_table")
    @Query("SELECT * FROM custom_table ORDER BY Id ASC")
    fun observeAll() : Flow<List<CustomEntity>>

    @Transaction
    suspend fun resetDAO() {
        deleteAll()
    }
}