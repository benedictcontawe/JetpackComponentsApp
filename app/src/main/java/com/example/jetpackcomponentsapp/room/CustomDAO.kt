package com.example.jetpackcomponentsapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
public interface CustomDAO {
    //@Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insert(customEntity: CustomEntity)

    @Update
    public suspend fun update(customEntity: CustomEntity)

    //@Delete
    //public suspend fun delete(customEntity: CustomEntity)

    @Query("DELETE FROM custom_table WHERE Id = :id")
    public suspend fun delete(id : Int?)

    @Query("DELETE FROM custom_table")
    public suspend fun deleteAll()

    @Query("SELECT * FROM custom_table GROUP BY Id ORDER BY Id ASC LIMIT :limit OFFSET :offset")
    public suspend fun getAll(limit : Int, offset : Int) : List<CustomEntity>
    /*
    @Query("SELECT * FROM custom_table GROUP BY Id ORDER BY Id ASC LIMIT :limit OFFSET :offset")
    public suspend fun getAllDataSource(limit : Int, offset : Int) : DataSource.Factory<Int, CustomEntity>
    */
    @Query("SELECT * FROM custom_table GROUP BY Id ORDER BY Id ASC")
    //public fun observeAll() : PagingSource<Int, CustomEntity>
    public fun observeAll() : Flow<List<CustomEntity>>
}