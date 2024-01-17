package com.example.jetpackcomponentsapp.model;

import com.example.jetpackcomponentsapp.R;
import androidx.annotation.NonNull;
public class CustomModel {

    public final int id;
    private String name;
    public final int icon;

    public CustomModel(String name) {
        this.id = 0;
        this.name = name;
        this.icon = R.drawable.ic_launcher_foreground;
    }

    public CustomModel(int id, String name) {
        this.id = id;
        this.name = name;
        this.icon = R.drawable.ic_launcher_foreground;
    }

    public CustomModel(int id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    @NonNull
    @Override
    public String toString() {
        return "CustomModel id " + this.id + ", name " + this.name + ", icon " + this.icon;
    }
}