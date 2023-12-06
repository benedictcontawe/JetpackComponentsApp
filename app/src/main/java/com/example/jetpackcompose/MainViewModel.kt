package com.example.jetpackcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel {

    private val data : MutableLiveData<String> = MutableLiveData<String>()
    private val progressData : MutableLiveData<Float> = MutableLiveData<Float>()
    private val liveSwitchChecked : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    public val radioButtonList : List<String> = listOf("On", "Off")
    private val liveSelectedRadioButton : MutableLiveData<String> = MutableLiveData<String>()
    private val liveDataCheckBoxChecked : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    public val spinnerList : List<String> = listOf("A", "B", "C", "D", "E", "F", "G")

    constructor() {

    }
    //region Data Methods
    public fun getData() : LiveData<String> {
        return data
    }

    public fun setData(data : String) {
        this.data.setValue(data)
    }

    public fun getProgressData() : LiveData<Float> {
        return progressData
    }

    public fun setProgressData(progressData : Float) {
        this.progressData.setValue(progressData)
    }
    //endregion
    //region Switch Methods
    public fun getSwitchChecked() : LiveData<Boolean> {
        return this.liveSwitchChecked
    }

    public fun setSwitchChecked(isChecked : Boolean) {
        liveSwitchChecked.setValue(isChecked)
        if (isChecked) {
            setData("Switch is On")
        } else {
            setData("Switch is Off")
        }
    }
    //endregion
    //region Radio Button Methods
    public fun getSelectedRadioButton() : LiveData<String> {
        return liveSelectedRadioButton
    }

    public fun setSelectedRadioButton(radioText : String) {
        if (radioText.contentEquals(radioButtonList.first())) {
            liveSelectedRadioButton.setValue(radioText)
            setData("Radio Button is On of your selected Radio Group")
        } else if (radioText.contentEquals(radioButtonList.last())) {
            liveSelectedRadioButton.setValue(radioText)
            setData("Radio Button is Off of your selected Radio Group")
        }
    }

    public fun isRadioButtonChecked(radioText : String) : Boolean {
        return liveSelectedRadioButton.getValue().contentEquals(radioText)
    }
    //endregion.
    //region Check Box Methods
    public fun setCheckBoxChecked(isChecked : Boolean) {
        liveDataCheckBoxChecked.setValue(isChecked)
        if (isChecked) {
            setData("Check Box is On")
        } else {
            setData("Check Box is Off")
        }
    }

    public fun getCheckBoxChecked() : LiveData<Boolean> {
        return liveDataCheckBoxChecked
    }

    public fun getCheckBoxText(isChecked : Boolean) : String {
        return if (isChecked) "On" else "Off"
    }
    //endregion
    override fun onCleared() {
        super.onCleared()
    }
}