package com.example.jetpackcomponentsapp.repository;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.model.PrimitiveModel;
import com.example.jetpackcomponentsapp.utils.Constants;
import com.example.jetpackcomponentsapp.utils.ConvertList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.apache.commons.lang3.StringUtils;

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
    //region Object Methods
    @Override
    public void createObject(Map<String, Object> data) {
        firebaseFirestore.collection(Constants.OBJECT).add(data);
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
    public void updateObject(CustomModel model, Consumer<Void> onSuccess, Consumer<Exception> onFailure) {
        firebaseFirestore.collection(Constants.OBJECT).document(model.id).update( ConvertList.toMap(model) )
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                onSuccess.accept(unused);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onFailure.accept(exception);
            }
        });
    }

    @Override
    public void updateObject(String newName, Uri newUri, CustomModel model, Consumer<Void> onSuccess, Consumer<Exception> onFailure) {
        updateFile(newName, newUri, model.file,
            newFile -> {
                updateObject (
                    new CustomModel (model.id, model.name, newFile.toString(), newName),
                    onSuccess,
                    onFailure
                );
            }, exception -> {
                onFailure.accept(exception);
            }
        );
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
    //endregion
    //region Primitive Methods
    @Override
    public void createPrimitive(Map<String, Object> data) {
        firebaseFirestore.collection(Constants.PRIMITIVE).add(data);
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
    //endregion
    //region File Methods
    @Override
    public void uploadFile(String name, Uri uri, Consumer<Uri> onSuccess, Consumer<Exception> onFailure) {
        if (StringUtils.isBlank(name)) onFailure.accept(new Exception("File name is Nil"));
        else if (uri == null) onFailure.accept(new Exception("File is Nil"));
        else {
            //imageReference.child(name).putData(bytes);
            imageReference.child(name).putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploadedFile(taskSnapshot, onSuccess, onFailure);
                    }
                } ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        onFailure.accept(exception);
                }
            });
        }
    }

    private void uploadedFile(UploadTask.TaskSnapshot taskSnapshot, Consumer<Uri> onSuccess, Consumer<Exception> onFailure) {
        taskSnapshot.getMetadata().getReference().getDownloadUrl()
        .addOnSuccessListener(new OnSuccessListener<Uri>() {
              @Override
              public void onSuccess(Uri uri) {
                  onSuccess.accept(uri);
              }
          }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onFailure.accept(exception);
            }
        });
    }

    @Override
    public void updateFile(String newName, Uri newUri, String oldName, Consumer<Uri> onSuccess, Consumer<Exception> onFailure) {
        if (StringUtils.isBlank(newName)) onFailure.accept(new Exception("File name is Nil"));
        else if (newName == null) onFailure.accept(new Exception("File is Nil"));
        else {
            imageReference.child(newName).putFile(newUri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploadedFile(taskSnapshot, onSuccess, onFailure);
                    try {
                        deleteImage(oldName);
                    } catch (Exception exception) {
                        onFailure.accept(exception);
                    }
                }
            } ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    onFailure.accept(exception);
                }
            });
        }
    }

    @Override
    public void deleteImage(String name) throws Exception {
        if (name == null) throw new Exception("Image is Nil");
        imageReference.child(name).delete();
    }

    public void deleteImages(Consumer<ListResult> onSuccess, Consumer<Exception> onFailure) {
        imageReference.listAll()
            .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    onSuccess.accept(listResult);
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
                    onFailure.accept(exception);
                }
            });
    }
    //endregion
}