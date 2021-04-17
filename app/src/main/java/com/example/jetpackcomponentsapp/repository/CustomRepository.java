package com.example.jetpackcomponentsapp.repository;

import android.app.Application;

import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.model.CustomModel;

import java.util.ArrayList;
import java.util.List;

public class CustomRepository implements BaseRepository {

    static volatile private  CustomRepository INSTANCE;

    static public CustomRepository getInstance(Application applicationContext) {
        if (INSTANCE != null)
            return INSTANCE;
        else {
            INSTANCE = new CustomRepository(applicationContext);
            return INSTANCE;
        }
    }

    public CustomRepository(Application applicationContext) {

    }

    private CustomModel getItem(int id) {
        return new CustomModel(id, String.valueOf(id), R.drawable.ic_android_black);
    }

    @Override
    public List<CustomModel> getItems() {
        List<CustomModel> list = new ArrayList<CustomModel>();
        for(int i = 0; i < 11; i++){
            list.add(getItem(i));
        }
        return list;
    }
}