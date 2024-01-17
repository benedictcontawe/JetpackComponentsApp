package com.example.jetpackcomponentsapp.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomModel {

    public final String id;
    public String name;
    @Nullable public final String icon;
    @Nullable public String file;

    public CustomModel(String name) {
        this.id = "Nil";
        this.name = name;
        this.icon = null;
    }

    public CustomModel(@Nullable String id, @Nullable String name, @Nullable String icon, @Nullable String file) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.file = file;
    }

    @NonNull
    @Override
    public String toString() {
        return "CustomModel id " + this.id + ", name " + this.name + ", icon " + this.icon;
    }
}