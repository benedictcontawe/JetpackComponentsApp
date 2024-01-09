package com.example.jetpackcomponentsapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomDAO {

    @Insert
    public void insert(CustomEntity customEntity);

    @Update
    public void update(CustomEntity customEntity);

    //@Delete
    //public fun delete(CustomEntity customEntity);

    @Query("DELETE FROM custom_table WHERE Id = :id")
    public void delete(int id);

    @Query("DELETE FROM custom_table")
    public void deleteAll();

    @Query("SELECT * FROM custom_table")
    public LiveData<List<CustomEntity>> getAll();
}