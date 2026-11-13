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
    public val ratingStarCount : Int = 5
    public val ratingStepSize : Float = 0.5f // like android:stepSize="0.5" on the XML RatingBar
    private val liveRating : MutableLiveData<Float> = MutableLiveData<Float>()
    private val liveSliderProgress : MutableLiveData<Float> = MutableLiveData<Float>()
    public val discreteSliderRange : ClosedFloatingPointRange<Float> = 0f..10f
    public val discreteSliderSteps : Int = 9 // 9 stops between 0 and 10 -> values 0, 1, 2, ... 10
    private val liveDiscreteSliderValue : MutableLiveData<Int> = MutableLiveData<Int>()
    public val rangeSliderRange : ClosedFloatingPointRange<Float> = 1f..10f
    public val rangeSliderSteps : Int = 8 // 8 stops between 1 and 10 -> values 1, 2, 3, ... 10
    private val liveRangeSliderValue : MutableLiveData<ClosedFloatingPointRange<Float>> = MutableLiveData<ClosedFloatingPointRange<Float>>()

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
    //region Toggle Button Methods
    public val toggleButtonTextOn : String = "ON"   // like android:textOn
    public val toggleButtonTextOff : String = "OFF" // like android:textOff
    private val liveToggleButtonChecked : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    public fun getToggleButtonChecked() : LiveData<Boolean> {
        return liveToggleButtonChecked
    }

    public fun setToggleButtonChecked(isChecked : Boolean) {
        liveToggleButtonChecked.setValue(isChecked)
        setData(if (isChecked) "Toggle Button is On" else "Toggle Button is Off")
    }

    public fun getToggleButtonText(isChecked : Boolean) : String {
        return if (isChecked) toggleButtonTextOn else toggleButtonTextOff
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

    // Observe this from Compose (observeAsState) so the RadioButtons recompose when the selection changes.
    public fun getSelectedRadioButton() : LiveData<String> {
        return liveSelectedRadioButton
    }

    public fun isRadioButtonChecked(selectedRadioText : String, radioText : String) : Boolean {
        return selectedRadioText.contentEquals(radioText)
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
        setData("Custom Spinner value selected is  ${spinnerList.get(customSpinnerSelectedIndex)}")
    }
    //endregion
    //region Rating Bar Methods
    public fun setRatingBar(rating : Float) {
        // Snap to the step size (0, 0.5, 1, 1.5 ... 5) and keep it inside 0..ratingStarCount.
        val steppedRating : Float = (Math.round(rating / ratingStepSize) * ratingStepSize)
            .coerceIn(0f, ratingStarCount.toFloat())
        if (liveRating.getValue() == steppedRating) {
            return
        }
        liveRating.setValue(steppedRating)
        setData("Rating Bar value is $steppedRating")
    }

    public fun getRatingBar() : LiveData<Float> {
        return liveRating
    }
    //endregion
    //region Seek Bar Methods
    public fun setSliderProgress(progress : Float) {
        liveSliderProgress.setValue(progress)
        progressData.setValue(progress)
        setData("Seek Bar Value selected is $progress")
    }

    public fun getSliderProgress() : LiveData<Float> {
        return liveSliderProgress
    }
    //endregion
    //region Discrete Seek Bar Methods
    public fun setDiscreteSliderValue(value : Float) {
        val discreteValue : Int = Math.round(value)
        liveDiscreteSliderValue.setValue(discreteValue)
        setData("Discrete Seek Bar Value selected is $discreteValue")
    }

    public fun getDiscreteSliderValue() : LiveData<Int> {
        return liveDiscreteSliderValue
    }
    //endregion
    //region Range Seek Bar Methods
    public fun setRangeSliderValue(newRange : ClosedFloatingPointRange<Float>) {
        // When both thumbs sit on the same value they overlap and the slider can't tell which one
        // you are grabbing, so always keep them at least one step apart.
        val minGap : Float = (rangeSliderRange.endInclusive - rangeSliderRange.start) / (rangeSliderSteps + 1)
        val oldRange : ClosedFloatingPointRange<Float> = liveRangeSliderValue.getValue() ?: rangeSliderRange
        val isTooClose : Boolean = newRange.endInclusive - newRange.start < minGap
        val isMinMoved : Boolean = newRange.start != oldRange.start
        // Only one thumb moves at a time: push back whichever one moved, one step away from the other.
        val start : Float = if (isTooClose && isMinMoved) newRange.endInclusive - minGap else newRange.start
        val end : Float = if (isTooClose && !isMinMoved) newRange.start + minGap else newRange.endInclusive
        val range : ClosedFloatingPointRange<Float> = start..end
        if (range == oldRange) {
            return
        }
        liveRangeSliderValue.setValue(range)
        setData("Range Seek Bar Value selected is ${Math.round(range.start)} - ${Math.round(range.endInclusive)}")
    }

    public fun getRangeSliderValue() : LiveData<ClosedFloatingPointRange<Float>> {
        return liveRangeSliderValue
    }
    //endregion
    override fun onCleared() {
        super.onCleared()
    }
}