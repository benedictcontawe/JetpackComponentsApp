package com.example.jetpackcomponentsapp.repository;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.model.PrimitiveModel;
import com.example.jetpackcomponentsapp.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import java.util.Map;

public class CustomRepository implements BaseRepository {

    private static CustomRepository INSTANCE;
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final StorageReference imageReference, videosReference;

    public static CustomRepository getInstance() {
        if (INSTANCE == null) INSTANCE = new CustomRepository();
        return INSTANCE;
    }

    private CustomRepository() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        imageReference = firebaseStorage.getReference().child("images/");
        videosReference = firebaseStorage.getReference().child("videos/");
    }

    @Override
    public void createObject(Map<String, Object> data) {
        firebaseFirestore.collection(Constants.OBJECT).add(data);
    }

    @Override
    public void createPrimitive(Map<String, Object> data) {
        firebaseFirestore.collection(Constants.PRIMITIVE).add(data);
    }

    @Override
    public void getObjects(Consumer<QuerySnapshot> onSuccess, Consumer<Exception> onFailure) {
        final Task<QuerySnapshot> response = firebaseFirestore.collection(Constants.OBJECT).get();
        response.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                onSuccess.accept(queryDocumentSnapshots);
            }
        } ). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onFailure.accept(exception);
            }
        } );
    }

    @Override
    public void updateObject(CustomModel model) throws Exception {
        if (model != null) {
            firebaseFirestore
                .collection(Constants.OBJECT)
                .document(model.id)
                .update( ConvertList.toMap(model) );
        } else {
            throw new Exception("CustomModel is Null");
        }
    }

    @Override
    public void deleteObject(CustomModel model) throws Exception {
        if(model != null) {
            firebaseFirestore
                .collection(Constants.OBJECT)
                .document(model.id)
                .delete();
        } else {
            throw new Exception("Error deleting model");
        }
    }

    @Override
    public void updatePrimitive(PrimitiveModel model) throws Exception {
        if (model != null) {
            firebaseFirestore
                .collection(Constants.PRIMITIVE)
                .document(model.id)
                .update( ConvertList.toMap(model) );
        } else {
            throw new Exception("PrimitiveModel is Nil");
        }
    }

    @Override
    public void getPrimitives(Consumer<QuerySnapshot> onSuccess, Consumer<Exception> onFailure) {
        final Task<QuerySnapshot> response = firebaseFirestore.collection(Constants.PRIMITIVE).get();
        response.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                onSuccess.accept(queryDocumentSnapshots);
            }
        } ). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onFailure.accept(exception);
            }
        } );
    }

    public void uploadFile() throws Exception {
        if (false) throw new Exception("File is Nil");
        //imageReference.child(name).putData(bytes);
    }

    public void deleteImage(String name) throws Exception {
        if (name == null) throw new Exception("Image is Nil");
        imageReference.child(name).delete();
    }

    public void deleteImages(Consumer<ListResult> onSuccess, Consumer<Exception> onFailure) {
        imageReference.listAll()
            .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    onSuccess(listResult);
                    /*
                    for (StorageReference reference : listResult.getItems()) {
                        reference.delete();
                    }
                    */
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    onFailure(exception);
                }
            });
    }
}