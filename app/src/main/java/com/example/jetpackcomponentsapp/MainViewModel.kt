package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val stringMediatorLiveData : MediatorLiveData<String> = MediatorLiveData<String>()
    val progressMediatorLiveData : MediatorLiveData<Int> = MediatorLiveData<Int>()

    val buttonLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val switchOnLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val switchOffLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val toggleButtonOnLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val toggleButtonOffLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val radioButtonOnLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val radioButtonOffLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val checkBoxOnLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val checkBoxOffLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val spinnerLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val customSpinnerLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val ratingBarLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val seekBarLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val seekBarProgressLiveData : MutableLiveData<Int> = MutableLiveData<Int>()
    val seekBarDiscreteLiveData : MutableLiveData<String> = MutableLiveData<String>()
    val seekBarDiscreteProgressLiveData : MutableLiveData<Int> = MutableLiveData<Int>()
}

/*
Reference
    https://medium.com/@elye.project/understanding-live-data-made-simple-a820fcd7b4d0
    https://github.com/elye/demo_android_livedata_illustration
    https://miro.medium.com/max/384/1*eMsbIZVbY36kC2CmuWwOBQ.gif
    https://miro.medium.com/max/384/1*Nn77SrwxLxugLpmIYcZ03A.gif

    https://android.jlelse.eu/lets-keep-activity-dumb-using-livedata-53468ed0dc1f

Note:
    There’s one caveat though. If the Fragment is not alive, and the data changes both on LiveDataA
    and LiveDataB, when the Fragment came alive, the MediatorLiveData will take the LiveData that is
    last added as source, i.e. LiveDataB.
 */