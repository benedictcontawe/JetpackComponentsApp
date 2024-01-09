package com.example.jetpackcomponentsapp.view;

import com.example.jetpackcomponentsapp.model.CustomModel;

public interface CustomListeners {

    public void onUpdate(CustomModel item, int position);

    public void onDelete(CustomModel item, int position);
}