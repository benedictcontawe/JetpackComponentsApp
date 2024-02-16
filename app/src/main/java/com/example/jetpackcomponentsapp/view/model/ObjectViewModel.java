package com.example.jetpackcomponentsapp.view.model;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.repository.BaseRepository;
import com.example.jetpackcomponentsapp.utils.ConvertList;
import com.example.jetpackcomponentsapp.repository.CustomRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ObjectViewModel extends AndroidViewModel {

    public static final String TAG = ObjectViewModel.class.getSimpleName();
    private final BaseRepository repository;
    private final MutableLiveData<Boolean> liveLoading;
    private final MutableLiveData<List<CustomModel>> liveList;
    private final MutableLiveData<CustomModel> liveUpdate;
    private final MutableLiveData<Uri> liveUri;

    public ObjectViewModel(Application application) {
        super(application);
        repository = CustomRepository.getInstance();
        liveLoading = new MutableLiveData<Boolean>();
        liveList = new MutableLiveData<List<CustomModel>>();
        liveUpdate = new MutableLiveData<CustomModel>();
        liveUri = new MutableLiveData<Uri>();
    }

    public LiveData<Boolean> observeLoading() {
        return liveLoading;
    }

    public void resetUri() {
        liveUri.setValue(null);
    }

    public void setUri(ActivityResult activityResult) {
        Log.d(TAG,"setUri Result Code " + activityResult.getResultCode());
        Intent intent = activityResult.getData();
        if (activityResult.getResultCode() == Activity.RESULT_OK) {
            Log.d(TAG,"setUri Data " + intent.getData());
            liveUri.setValue(intent.getData());
        }
    }

    public LiveData<Uri> observeUri() {
        return liveUri;
    }

    public void fetchItems() {
        liveLoading.setValue(true);
        repository.getObjects (
            queryDocumentSnapshots -> {
                Log.d(TAG,"fetchItems onSuccess");
                List<CustomModel> list = new ArrayList<>();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Log.d(TAG,"fetchItems onSuccess stream");
                    list = queryDocumentSnapshots.getDocuments().stream()
                        .map(ConvertList::fromObjectSnapshot)//.map(doc -> ConvertList.fromObjectSnapshot(doc))
                        .collect(Collectors.toList());
                } else {
                    Log.d(TAG,"fetchItems onSuccess for each");
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        list.add (ConvertList.fromObjectSnapshot(snapshot));
                    }
                }
                liveList.setValue(list);
                liveLoading.setValue(false);
                /*
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                */
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();
                */
            }, exception -> {
                Log.e(TAG,"fetchItems onFailure Exception " + exception.getMessage(), exception);
                liveLoading.setValue(false);
            }
        );
    }

    public void setUpdate(CustomModel item) {
        liveUpdate.setValue(item);
    }

    public LiveData<CustomModel> getUpdate() {
        return liveUpdate;
    }

    public void insertItem(CustomModel model) {
        String fileName = getFileName(liveUri.getValue());
        liveLoading.setValue(true);
        repository.uploadFile(fileName, liveUri.getValue(), uri -> {
            Log.d(TAG,"insertItem uploadFile onSuccess");
            repository.createObject (
                ConvertList.toMap (
                    new CustomModel (
                        model.id,
                        model.name,
                        uri.toString(),
                        fileName
                    )
                )
            );
            resetUri();
            liveLoading.setValue(false);
            fetchItems();
        }, exception -> {
            Log.e(TAG,"insertItem uploadFile onFailure Exception " + exception.getMessage(), exception);
            liveLoading.setValue(false);
        } );
    }

    public void updateItem(String name) {
        liveLoading.setValue(true);
        CustomModel model = new CustomModel (
            liveUpdate.getValue().id, name, liveUpdate.getValue().icon, liveUpdate.getValue().file
        );
        if (liveUri.getValue() == null) {
            repository.updateObject ( model, unused -> {
                liveLoading.setValue(false);
                fetchItems();
            }, exception -> {
                Log.e(TAG,"updateItem updateObject onFailure Exception " + exception.getMessage(), exception);
                liveLoading.setValue(false);
                fetchItems();
            } );
        } else if (liveUri.getValue() != null) {
            repository.updateObject (
                getFileName(liveUri.getValue()),
                liveUri.getValue(), model, unused -> {
                    liveLoading.setValue(false);
                    fetchItems();
                }, exception -> {
                    Log.e(TAG,"updateItem updateObject onFailure Exception " + exception.getMessage(), exception);
                    liveLoading.setValue(false);
                    fetchItems();
                }
            );
        }
    }
    public void deleteItem(CustomModel model) {
        try {
            liveLoading.setValue(true);
            repository.deleteObject(model);
            repository.deleteImage(model.file);
        } catch (Exception exception) {
            Log.e(TAG,"deleteItem Exception " + exception.getMessage(), exception);
        } finally {
            liveLoading.setValue(false);
            fetchItems();
        }
    }

    public void deleteAll() {
        try {
            liveLoading.setValue(true);
            for (CustomModel model : liveList.getValue()) {
                Log.d(TAG,"deleteAll " + model);
                repository.deleteObject(model);
                repository.deleteImage(model.file);
            }
        } catch (Exception exception) {
            Log.e(TAG,"deleteAll Exception " + exception.getMessage(), exception);
        } finally {
            liveLoading.setValue(false);
            fetchItems();
        }
    }

    public LiveData<List<CustomModel>> getItems() {
        return liveList;
    }

    private String getFileName(Uri uri) {
        if (uri == null) return null;
        switch (uri.getScheme()) {
            case ContentResolver.SCHEME_FILE:
                return new File(uri.getPath()).getName();
            case ContentResolver.SCHEME_CONTENT:
                Cursor cursor = getApplication().getContentResolver().query(
                        uri,
                        new String[]{OpenableColumns.DISPLAY_NAME},
                        null,
                        null,
                        null
                );
                if (cursor == null) {
                    //throw new Exception("Failed to obtain cursor from the content resolver");
                    return null;
                }
                try {
                    cursor.moveToFirst();
                    if (cursor.getCount() == 0) {
                        //throw new Exception("The given Uri doesn't represent any file");
                        return null;
                    }
                    int displayNameColumnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    return cursor.getString(displayNameColumnIndex);
                } finally {
                    cursor.close();
                }
            case ContentResolver.SCHEME_ANDROID_RESOURCE:
                String lastPathSegment = uri.getLastPathSegment();
                Integer resourceId = lastPathSegment != null ? Integer.valueOf(lastPathSegment) : null;
                if (resourceId != null) {
                    return getApplication().getResources().getResourceName(resourceId);
                } else {
                    String packageName = uri.getAuthority();
                    String resourceType = uri.getPathSegments().get(0);
                    String resourceEntryName = uri.getPathSegments().get(1);
                    resourceId = getApplication().getResources().getIdentifier (
                        resourceEntryName, resourceType, packageName
                    );
                    return getApplication().getResources().getResourceName(resourceId);
                }
            default: // probably a http uri
                //return uri.toString().substringBeforeLast(".").substringAfterLast("/");
                return uri.toString().substring(0, uri.toString().lastIndexOf(".")).substring(uri.toString().lastIndexOf("/") + 1);
        }
    }
}