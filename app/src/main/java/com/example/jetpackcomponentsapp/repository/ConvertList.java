package com.example.jetpackcomponentsapp.repository;

import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.model.PrimitiveModel;
import com.example.jetpackcomponentsapp.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.HashMap;
import java.util.Map;

public class ConvertList {

    public static CustomModel fromObjectSnapshot(DocumentSnapshot snapshot) {
        final Map<String, Object> data = snapshot.getData();
        return new CustomModel (
            snapshot.getId(),
            getStringOrNull(data, Constants.NAME),
            getStringOrNull(data, Constants.IMAGE_URL),
            getStringOrNull(data, Constants.IMAGE_NAME)
        );
    }

    public static PrimitiveModel fromPrimitiveSnapshot(DocumentSnapshot snapshot) {
        final Map<String, Object> data = snapshot.getData();
        if (getStringOrNull(data, Constants.BOOLEAN) != null) {
            return new PrimitiveModel (
                snapshot.getId(),
                getStringOrNull(data, Constants.BOOLEAN),
                Constants.BOOLEAN
            );
        } else if (getStringOrNull(data, Constants.STRING) != null) {
            return new PrimitiveModel (
                snapshot.getId(),
                getStringOrNull(data, Constants.STRING),
                Constants.STRING
            );
        } else if (getStringOrNull(data, Constants.INTEGER) != null) {
            return new PrimitiveModel (
                snapshot.getId(),
                getStringOrNull(data, Constants.INTEGER),
                Constants.INTEGER
            );
        } else if (getStringOrNull(data, Constants.DOUBLE) != null) {
            return new PrimitiveModel (
                snapshot.getId(),
                getStringOrNull(data, Constants.DOUBLE),
                Constants.DOUBLE
            );
        } else {
            return new PrimitiveModel (
                snapshot.getId(),
                null,
                null
            );
        }
    }

    public static Map<String, Object> toMap(CustomModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.NAME, model.name);
        map.put(Constants.IMAGE_URL, model.icon);
        map.put(Constants.IMAGE_NAME, model.file);
        return map;
    }

    public static Map<String, Object> toMap(PrimitiveModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (model.type.contentEquals(Constants.BOOLEAN)) {
            map.put(Constants.BOOLEAN, model.data);
        } else if (model.type.contentEquals(Constants.STRING)) {
            map.put(Constants.STRING, model.data);
        } else if (model.type.contentEquals(Constants.INTEGER)) {
            map.put(Constants.INTEGER, model.data);
        } else if (model.type.contentEquals(Constants.DOUBLE)) {
            map.put(Constants.DOUBLE, model.data);
        }
        return map;
    }

    private static String getStringOrNull(Map<String, Object> data, String key) {
        if (data != null && data.containsKey(key)) {
            Object value = data.get(key);
            return (value != null) ? value.toString() : null;
        }
        return null;
    }
}