package com.example.jetpackcomponentsapp.view.model;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.repository.BaseRepository;
import com.example.jetpackcomponentsapp.repository.ConvertList;
import com.example.jetpackcomponentsapp.repository.CustomRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectViewModel extends ViewModel {

    public static final String TAG = ObjectViewModel.class.getSimpleName();
    private final BaseRepository repository;
    private List<CustomModel> list = new ArrayList<>();
    private final MutableLiveData<Boolean> liveLoading;
    private final MutableLiveData<List<CustomModel>> liveList;
    private final MutableLiveData<CustomModel> liveUpdate;

    public ObjectViewModel() {
        super();
        repository = CustomRepository.getInstance();
        liveLoading = new MutableLiveData<Boolean>();
        liveList = new MutableLiveData<List<CustomModel>>();
        liveUpdate = new MutableLiveData<>();
    }

    public LiveData<Boolean> observeLoading() {
        return liveLoading;
    }

    public void fetchItems() {
        liveLoading.setValue(true);
        repository.getObjects (
            queryDocumentSnapshots -> {
                Log.d(TAG,"setItems onSuccess");
                list.clear();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Log.d(TAG,"setItems onSuccess stream");
                    list = queryDocumentSnapshots.getDocuments().stream()
                        .map(ConvertList::fromObjectSnapshot)//.map(doc -> ConvertList.fromObjectSnapshot(doc))
                        .collect(Collectors.toList());
                } else {
                    Log.d(TAG,"setItems onSuccess for each");
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        list.add (ConvertList.fromObjectSnapshot(snapshot));
                    }
                }
                liveList.setValue(list);
                liveLoading.setValue(false);
            },
            exception -> {
                Log.e(TAG,"setItems onFailure Exception " + exception.getMessage(), exception);
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
        try {
            liveLoading.setValue(true);
            repository.createObject( ConvertList.toMap(model) );
            //TODO: Insert Firebase Storage Image
        } catch (Exception exception) {
            Log.e(TAG,"insertItem Exception " + exception.getMessage(), exception);
        } finally {
            liveLoading.setValue(false);
            fetchItems();
        }
    }

    public void updateItem() {
        try {
            liveLoading.setValue(true);
            CustomModel model = liveUpdate.getValue();
            repository.updateObject(model);
            //TODO: Update Firebase Storage Image
            //TODO: Delete Old Firebase Storage Image repository.deleteImage(model.file);
        } catch (Exception exception) {
            Log.e(TAG,"updateItem Exception " + exception.getMessage(), exception);
        } finally {
            liveLoading.setValue(false);
            fetchItems();
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
            for (CustomModel model : list) {
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
}