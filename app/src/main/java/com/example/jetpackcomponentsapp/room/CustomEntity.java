package com.example.jetpackcomponentsapp.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.jetpackcomponentsapp.R;

@Entity(tableName = "custom_table")
public class CustomEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Icon")
    private int icon;

    @Ignore
    public CustomEntity(String name) {
        this.name = name;
        this.icon = R.drawable.ic_launcher_foreground;
    }

    public CustomEntity(String name,int icon) {
        this.name = name;
        this.icon = icon;
    }

    @Ignore
    public CustomEntity(int id, String name,int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}