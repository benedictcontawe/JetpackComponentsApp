package com.example.jetpackcomponentsapp.view.model;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.jetpackcomponentsapp.model.PrimitiveModel;
import com.example.jetpackcomponentsapp.repository.BaseRepository;
import com.example.jetpackcomponentsapp.utils.ConvertList;
import com.example.jetpackcomponentsapp.repository.CustomRepository;
import com.example.jetpackcomponentsapp.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrimitiveViewModel extends ViewModel {

    public static final String TAG = PrimitiveViewModel.class.getSimpleName();
    private final BaseRepository repository;
    private final List<PrimitiveModel> primitives = new ArrayList<>();
    private final MutableLiveData<Boolean> _liveBoolean;
    private final MutableLiveData<String> _liveString;
    private final MutableLiveData<Integer> _liveInteger;
    private final MutableLiveData<Double> _liveDouble;

    public PrimitiveViewModel() {
        super();
        repository = CustomRepository.getInstance();
        _liveBoolean = new MutableLiveData<Boolean>();
        _liveString = new MutableLiveData<String>();
        _liveInteger = new MutableLiveData<Integer>();
        _liveDouble = new MutableLiveData<Double>();
    }

    public void fetchData() {
        repository.getPrimitives (
            queryDocumentSnapshots -> {
                Log.d(TAG,"fetchData onSuccess");
                primitives.clear();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Log.d(TAG,"fetchData onSuccess stream");
                    queryDocumentSnapshots.getDocuments().stream().forEach(doc -> {
                        PrimitiveModel model = ConvertList.fromPrimitiveSnapshot(doc);
                        primitives.add(model);
                        if (model.type == Constants.BOOLEAN) {
                            _liveBoolean.setValue(Boolean.valueOf(model.data.toString()));
                        } else if (model.type == Constants.STRING) {
                            _liveString.setValue(model.data.toString());
                        } else if (model.type == Constants.INTEGER) {
                            _liveInteger.setValue(Integer.valueOf(model.data.toString()));
                        } else if (model.type == Constants.DOUBLE) {
                            _liveDouble.setValue(Double.valueOf(model.data.toString()));
                        }
                    } );
                } else {
                    Log.d(TAG,"fetchData onSuccess for each");
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        PrimitiveModel model = ConvertList.fromPrimitiveSnapshot(snapshot);
                        primitives.add(model);
                        if (model.type == Constants.BOOLEAN) {
                            _liveBoolean.setValue(Boolean.valueOf(model.data.toString()));
                        } else if (model.type == Constants.STRING) {
                            _liveString.setValue(model.data.toString());
                        } else if (model.type == Constants.INTEGER) {
                            _liveInteger.setValue(Integer.valueOf(model.data.toString()));
                        } else if (model.type == Constants.DOUBLE) {
                            _liveDouble.setValue(Double.valueOf(model.data.toString()));
                        }
                    }
                }

            },
            exception -> {
                Log.e(TAG,"setItems onFailure Exception " + exception.getMessage(), exception);
            }
        );
    }
    //region Update Methods
    public void update(Boolean value) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        //Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (value != null) {
                        for (PrimitiveModel model : primitives) {
                            if (model.type == Constants.BOOLEAN) {
                                repository.updatePrimitive (
                                        new PrimitiveModel (
                                                model.id,
                                                value,
                                                Constants.BOOLEAN
                                        )
                                );
                                break;
                            }
                        }
                    }
                } catch (Exception exception) {
                    Log.e(TAG,"Invalid Boolean Update Exception " + exception.getMessage(), exception);
                } finally {
                    fetchData();
                }
                /*
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                */
            }
        });
    }

    public void update(String value) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (value != null) {
                        for (PrimitiveModel model : primitives) {
                            if (model.type == Constants.STRING) {
                                repository.updatePrimitive (
                                        new PrimitiveModel (
                                                model.id,
                                                value,
                                                Constants.STRING
                                        )
                                );
                                break;
                            }
                        }
                    }
                } catch (Exception exception) {
                    Log.e(TAG,"Invalid String Update Exception " + exception.getMessage(), exception);
                } finally {
                    fetchData();
                }
            }
        });
    }

    public void update(Integer value) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (value != null) {
                        for (PrimitiveModel model : primitives) {
                            if (model.type == Constants.INTEGER) {
                                repository.updatePrimitive (
                                        new PrimitiveModel (
                                                model.id,
                                                value,
                                                Constants.INTEGER
                                        )
                                );
                                break;
                            }
                        }
                    }
                } catch (Exception exception) {
                    Log.e(TAG,"Invalid Integer Update Exception " + exception.getMessage(), exception);
                } finally {
                    fetchData();
                }
            }
        });
    }

    public void update(Double value) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (value != null) {
                        for (PrimitiveModel model : primitives) {
                            if (model.type == Constants.DOUBLE) {
                                repository.updatePrimitive (
                                        new PrimitiveModel (
                                                model.id,
                                                value,
                                                Constants.DOUBLE
                                        )
                                );
                                break;
                            }
                        }
                    }
                } catch (Exception exception) {
                    Log.e(TAG,"Invalid Double Update Exception " + exception.getMessage(), exception);
                } finally {
                    fetchData();
                }
            }
        });
    }
    //endregion
    //region Observe Method
    public LiveData<Boolean> observeBoolean() {
        return _liveBoolean;
    }

    public LiveData<String> observeString() {
        return _liveString;
    }

    public LiveData<Integer> observeInt() {
        return _liveInteger;
    }

    public LiveData<Double> observeDouble() {
        return _liveDouble;
    }
    //endregion
}