package com.example.jetpackcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel {

    private val data : MutableLiveData<String> = MutableLiveData<String>()
    private val progressData : MutableLiveData<Float> = MutableLiveData<Float>()
    private val liveSwitchChecked : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    public val onOffList : List<String> = listOf("On", "Off")
    private val liveSelectedRadioButton : MutableLiveData<String> = MutableLiveData<String>()
    private val liveDataCheckBoxChecked : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val liveSpinnerExpanded : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    public val spinnerList : List<String> = listOf("A", "B", "C", "D", "E", "F", "G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
    private var spinnerSelectedIndex : Int = 0
    private val liveCustomSpinnerExpanded : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var customSpinnerSelectedIndex : Int = 0

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
    public fun setSelectedRadioButton(radioText : String) {
        if (radioText.contentEquals(onOffList.first())) {
            liveSelectedRadioButton.setValue(radioText)
            setData("Radio Button is On of your selected Radio Group")
        } else if (radioText.contentEquals(onOffList.last())) {
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
        return if (isChecked) onOffList.first() else onOffList.last()
    }
    //endregion
    //region Spinner Methods
    public fun setSpinnerExpanded(isExpanded : Boolean) {
        liveSpinnerExpanded.setValue(isExpanded)
        liveSpinnerExpanded.value?.not()
    }

    public fun getSpinnerExpanded() : LiveData<Boolean> {
        return liveSpinnerExpanded
    }

    public fun getSelectedSpinner(index : Int = spinnerSelectedIndex) : String {
        return spinnerList.get(index)
    }

    public fun setSpinnerSelectedIndex(value : String) {
        spinnerSelectedIndex = spinnerList.indexOf(value)
        setData("Spinner value selected is  ${spinnerList.get(spinnerSelectedIndex)}")
    }
    //endregion
    //region Custom Spinner Methods
    public fun setCustomSpinnerExpanded(isExpanded : Boolean) {
        liveCustomSpinnerExpanded.setValue(isExpanded)
        liveCustomSpinnerExpanded.value?.not()
    }

    public fun getCustomSpinnerExpanded() : LiveData<Boolean> {
        return liveCustomSpinnerExpanded
    }

    public fun getSelectedCustomSpinner(index : Int = customSpinnerSelectedIndex) : String {
        return spinnerList.get(index)
    }

    public fun setCustomSpinnerSelectedIndex(value : String) {
        customSpinnerSelectedIndex = spinnerList.indexOf(value)
        setData("Spinner value selected is  ${spinnerList.get(customSpinnerSelectedIndex)}")
    }
    //endregion
    //region Rating Bar Methods

    //endregion
    //region Seek Bar Methods

    //endregion
    //region Discrete Seek Bar Methods

    //endregion
    override fun onCleared() {
        super.onCleared()
    }
}