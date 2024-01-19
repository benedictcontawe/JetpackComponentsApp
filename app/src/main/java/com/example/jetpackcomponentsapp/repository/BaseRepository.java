package com.example.jetpackcomponentsapp.repository;

import android.net.Uri;
import androidx.core.util.Consumer;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.model.PrimitiveModel;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Map;

public interface BaseRepository {

    public void createObject(Map<String, Object> data);

    public void getObjects(Consumer<QuerySnapshot> onSuccess, Consumer<Exception> onFailure);

    public void updateObject(CustomModel model, Consumer<Void> onSuccess, Consumer<Exception> onFailure);

    public void updateObject(String newName, Uri newUri, CustomModel model, Consumer<Void> onSuccess, Consumer<Exception> onFailure);

    public void deleteObject(CustomModel customModel) throws Exception;

    public void createPrimitive(Map<String, Object> data);

    public void updatePrimitive(PrimitiveModel model) throws Exception;

    public void getPrimitives(Consumer<QuerySnapshot> onSuccess, Consumer<Exception> onFailure);

    public void uploadFile(String name, Uri uri, Consumer<Uri> onSuccess, Consumer<Exception> onFailure);

    public void updateFile(String name, Uri uri, String oldFile, Consumer<Uri> onSuccess, Consumer<Exception> onFailure);

    public void deleteImage(String name) throws Exception;
}