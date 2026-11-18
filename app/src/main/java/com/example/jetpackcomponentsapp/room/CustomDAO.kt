package com.example.jetpackcomponentsapp.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
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

    @Query("SELECT * FROM custom_table GROUP BY Id ORDER BY Id ASC LIMIT :limit OFFSET :offset")
    public suspend fun getAll(limit : Int, offset : Int) : List<CustomEntity>

    //@Query("SELECT * FROM custom_table")
    @Query("SELECT * FROM custom_table ORDER BY Id ASC")
    fun observeLiveAll() : LiveData<List<CustomEntity>>

    @Query("SELECT * FROM custom_table GROUP BY Id ORDER BY Id ASC")
    public fun observeFlowAll() : Flow<List<CustomEntity>>

    // Room generates this PagingSource: it reads one page at a time (LIMIT / OFFSET) and is
    // invalidated automatically after insert / update / delete, so the list refreshes by itself.
    @Query("SELECT * FROM custom_table ORDER BY Id ASC")
    public fun observePagingSource() : PagingSource<Int, CustomEntity>

    @Transaction
    suspend fun resetDAO() {
        deleteAll()
    }
}