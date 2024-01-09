package com.example.jetpackcomponentsapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.room.CustomEntity;

import java.util.ArrayList;
import java.util.List;

public class ConvertList {

    public static List<CustomModel> toListModel(List<CustomEntity> customEntity) {
        List<CustomModel> itemList = new ArrayList<>();

        for (CustomEntity itemMap : customEntity) {
            itemList.add(
                    new CustomModel(itemMap.getId(),itemMap.getName())
            );
        }

        return itemList;
    }
    public static LiveData<List<CustomModel>> toLiveDataListModel(LiveData<List<CustomEntity>> localList) {
        return Transformations.map(
                localList,
                ConvertList::toListModel
        );
    }

    public static CustomEntity toEntity(CustomModel customModel) {
        if (customModel.getId() <= 0) {
            return new CustomEntity(
                customModel.getName(),
                customModel.getIcon()
            );
        } else {
            return new CustomEntity(
                customModel.getId(),
                customModel.getName(),
                customModel.getIcon()
            );
        }
    }
}