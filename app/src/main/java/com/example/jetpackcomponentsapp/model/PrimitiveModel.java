package com.example.jetpackcomponentsapp.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PrimitiveModel {

    public final String id;
    public final Object data;
    @Nullable
    public final String type;

    public PrimitiveModel(String id, Object data, String type) {
        this.id = id;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "PrimitiveModel id " + this.id + ", data " + this.data + ", type " + this.type;
    }
}