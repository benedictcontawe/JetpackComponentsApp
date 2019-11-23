package com.example.jetpackcomponentsapp;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public MediatorLiveData<String> stringMediatorLiveData = new MediatorLiveData<String>();
    public MediatorLiveData<Integer> progressMediatorLiveData  = new MediatorLiveData<Integer>();

    public MutableLiveData<String> buttonLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> switchOnLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> switchOffLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> toggleButtonOnLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> toggleButtonOffLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> radioButtonOnLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> radioButtonOffLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> checkBoxOnLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> checkBoxOffLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> spinnerLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> customSpinnerLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> ratingBarLiveData = new  MutableLiveData<String>();
    public MutableLiveData<String> seekBarLiveData = new  MutableLiveData<String>();
    public MutableLiveData<Integer> seekBarProgressLiveData = new  MutableLiveData<Integer>();
    public MutableLiveData<String> seekBarDiscreteLiveData = new  MutableLiveData<String>();
    public MutableLiveData<Integer> seekBarDiscreteProgressLiveData = new  MutableLiveData<Integer>();
}
