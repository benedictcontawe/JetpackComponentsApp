package com.example.jetpackcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.ui.theme.JetpackcomposeTheme

class MainActivity : ComponentActivity() {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
        public fun newIntent(context : Context) : Intent = Intent(context.applicationContext, MainActivity::class.java)
    }

    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel : MainViewModel = viewModel { MainViewModel() }
            val data : String by viewModel.getData().observeAsState("Result")
            val progress : Float by viewModel.getProgressData().observeAsState(0.0f)
            val isSwitchChecked : Boolean by viewModel.getSwitchChecked().observeAsState(false)
            val isToggleButtonChecked : Boolean by viewModel.getToggleButtonChecked().observeAsState(false)
            val selectedRadioButton : String by viewModel.getSelectedRadioButton().observeAsState("")
            val isCheckBoxChecked : Boolean by viewModel.getCheckBoxChecked().observeAsState(false)
            val isSpinnerExpanded : Boolean by viewModel.getSpinnerExpanded().observeAsState(false)
            val isCustomSpinnerExpanded : Boolean by viewModel.getCustomSpinnerExpanded().observeAsState(false)
            val rate : Float by viewModel.getRatingBar().observeAsState(0f)
            val sliderProgress : Float by viewModel.getSliderProgress().observeAsState(0.0f)
            val discreteSliderValue : Int by viewModel.getDiscreteSliderValue().observeAsState(0)
            val rangeSliderValue : ClosedFloatingPointRange<Float> by viewModel.getRangeSliderValue().observeAsState(viewModel.rangeSliderRange)
            JetpackcomposeTheme {
                SurfaceComposable(viewModel, data, progress, isSwitchChecked, isToggleButtonChecked, selectedRadioButton, isCheckBoxChecked, isSpinnerExpanded, isCustomSpinnerExpanded, rate, sliderProgress, discreteSliderValue, rangeSliderValue)
            }
        }
    }

    @Composable
    private fun SurfaceComposable(viewModel : MainViewModel, data : String, progress : Float, isSwitchChecked : Boolean, isToggleButtonChecked : Boolean, selectedRadioButton : String, isCheckBoxChecked : Boolean, isSpinnerExpanded : Boolean, isCustomSpinnerExpanded : Boolean, rate : Float, sliderProgress : Float, discreteSliderValue : Int, rangeSliderValue : ClosedFloatingPointRange<Float>) {
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = data, textAlign = TextAlign.Center, fontSize = 13.sp)
                LinearProgressIndicator (
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(13.dp)
                )
                Divider (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    //color = Color.Red
                )
                //Spacer(modifier = Modifier.height(0.dp))
                //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                Button (
                    onClick = { viewModel.setData("Button Was Clicked") }
                ) {
                    Text(text = "Send Data")
                }
                SwitchComposable(
                    isCheck = isSwitchChecked,
                    onCheckedChange = { isChecked : Boolean ->
                        viewModel.setSwitchChecked(isChecked)
                    }
                )
                ToggleButtonComposable (
                    isChecked = isToggleButtonChecked,
                    onCheckedChange = { isChecked : Boolean ->
                        viewModel.setToggleButtonChecked(isChecked)
                    },
                    text = viewModel.getToggleButtonText(isToggleButtonChecked)
                )
                RadioGroupComposable (
                    list = viewModel.onOffList,
                    onSelect = { option : String -> viewModel.isRadioButtonChecked(selectedRadioButton, option) },
                    onClick = { option : String -> viewModel.setSelectedRadioButton(option) },
                )
                CheckboxComposable (
                    isChecked = isCheckBoxChecked,
                    onCheckedChange = { isChecked : Boolean ->
                        viewModel.setCheckBoxChecked(isChecked)
                    },
                    text = viewModel.getCheckBoxText(isCheckBoxChecked)
                )
                SpinnerComposable (
                    onClick = { viewModel.setSpinnerExpanded(isSpinnerExpanded.not()) },
                    text = viewModel.getSelectedSpinner(),
                    isExpanded = isSpinnerExpanded,
                    onDismiss = { viewModel.setSpinnerExpanded(false) },
                    list = viewModel.spinnerList,
                    onItemSelected = { spinner : String ->
                        viewModel.setSpinnerExpanded(false)
                        viewModel.setSpinnerSelectedIndex(spinner)
                    }
                )
                CustomSpinnerComposable (
                    onClick = { viewModel.setCustomSpinnerExpanded(isCustomSpinnerExpanded.not()) },
                    text = viewModel.getSelectedCustomSpinner(),
                    isExpanded = isCustomSpinnerExpanded,
                    onDismiss = { viewModel.setCustomSpinnerExpanded(false) },
                    list = viewModel.spinnerList,
                    onItemSelected = { spinner : String ->
                        viewModel.setCustomSpinnerExpanded(false)
                        viewModel.setCustomSpinnerSelectedIndex(spinner)
                    }
                )
                RatingBarComposable (
                    rated = rate,
                    starCount = viewModel.ratingStarCount,
                    stepSize = viewModel.ratingStepSize,
                    onRatingChanged = { rating : Float -> viewModel.setRatingBar(rating) }
                )
                SeekBarComposable (
                    progressed = sliderProgress,
                    onProgressChanged = { progress : Float ->
                        viewModel.setSliderProgress(progress)
                    }
                )
                DiscreteSeekBarComposable (
                    value = discreteSliderValue,
                    valueRange = viewModel.discreteSliderRange,
                    steps = viewModel.discreteSliderSteps,
                    onValueChanged = { value : Float ->
                        viewModel.setDiscreteSliderValue(value)
                    }
                )
                RangeSliderComposable (
                    range = rangeSliderValue,
                    valueRange = viewModel.rangeSliderRange,
                    steps = viewModel.rangeSliderSteps,
                    onRangeChanged = { range : ClosedFloatingPointRange<Float> ->
                        viewModel.setRangeSliderValue(range)
                    }
                )
            }
        }
    }

    @Composable
    private fun SwitchComposable(isCheck : Boolean, onCheckedChange : (Boolean) -> Unit) {
        Switch (
            checked = isCheck,
            onCheckedChange = { isChecked : Boolean -> onCheckedChange(isChecked) },
            /*
            colors = SwitchDefaults.colors (
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = Color.Gray
            )
            */
        )
    }

    // Compose version of the XML <ToggleButton> (android:Theme.Material look): a raised button with
    // textOn / textOff and a thin indicator line at the bottom (green when checked, gray when unchecked).
    @Composable
    private fun ToggleButtonComposable(isChecked : Boolean, onCheckedChange : (Boolean) -> Unit, text : String) {
        Button (
            onClick = { onCheckedChange(isChecked.not()) },
            shape = RoundedCornerShape(2.dp),
            colors = ButtonDefaults.buttonColors (
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 6.dp),
            contentPadding = PaddingValues(0.dp),
            // TalkBack reads it as a toggle ("On" / "Off") instead of a plain button, like the XML ToggleButton.
            modifier = Modifier.semantics {
                toggleableState = ToggleableState(isChecked)
                stateDescription = text
            }
        ) {
            Box (
                modifier = Modifier.defaultMinSize(minWidth = 88.dp, minHeight = 36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text (
                    text = text.uppercase(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                // Indicator line along the bottom edge of the button.
                Box (
                    modifier = Modifier
                        .matchParentSize()
                        .wrapContentHeight(Alignment.Bottom)
                        .fillMaxWidth()
                        .height(1.5.dp)
                        .background(if (isChecked) Color.Green else Color.DarkGray)
                )
            }
        }
    }
    @Composable
    private fun RadioGroupComposable(list : List<String>, onSelect : (String) -> Boolean, onClick : (String) -> Unit) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            list.forEach { option ->
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton (
                        selected = onSelect(option),
                        onClick = { onClick(option) },
                        enabled = true,
                        //colors = RadioButtonDefaults.colors (selectedColor = Color.Magenta)
                    )
                    Text(text = option, textAlign = TextAlign.Center, fontSize = 13.sp)
                }
            }
        }
    }

    @Composable
    private fun CheckboxComposable(isChecked : Boolean, onCheckedChange : (Boolean) -> Unit, text : String) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox (
                checked = isChecked,
                onCheckedChange = { isChecked : Boolean -> onCheckedChange(isChecked) }
            )
            Text (
                text = text,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun SpinnerComposable(onClick : () -> Unit, text : String, isExpanded : Boolean, onDismiss : () -> Unit, list : List<String>, onItemSelected : (String) -> Unit) {
        Column() {
            Box (
                modifier = Modifier
                    .height(56.dp)/*.background(Color.Gray)*/
                    .clickable { onClick() }
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text)
                    Icon (
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
            DropdownMenu (
                expanded = isExpanded,
                onDismissRequest = { onDismiss() },
                //modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { spinner : String ->
                    DropdownMenuItem (
                        onClick = { onItemSelected(spinner) },
                        text = { Text(spinner) }
                    )
                }
            }
        }
    }

    @Composable
    private fun CustomSpinnerComposable(onClick : () -> Unit, text : String, isExpanded : Boolean, onDismiss : () -> Unit, list : List<String>, onItemSelected : (String) -> Unit) {
        Column() {
            Box (
                modifier = Modifier.height(56.dp).clickable { onClick() }
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon (
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                    Text(text)
                    Icon (
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
            DropdownMenu (
                expanded = isExpanded,
                onDismissRequest = { onDismiss() },
                //modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { spinner : String ->
                    DropdownMenuItem (
                        onClick = { onItemSelected(spinner) },
                        text = {
                            Row {
                                Icon (
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null
                                )
                                Text(spinner)
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun RatingBarComposable(rated : Float, starCount : Int, stepSize : Float, onRatingChanged : (Float) -> Unit) {
        val outlinedStar = painterResource(id = R.drawable.outlined_star)
        val filledStar = painterResource(id = R.drawable.filled_star)
        // Width of the row of stars in pixels, used to turn a touch position into a rating.
        var starsWidth : Int by remember { mutableStateOf(0) }
        // pointerInput(Unit) keeps running between recompositions, so read the latest values through these.
        val currentRated : Float by rememberUpdatedState(rated)
        val currentOnRatingChanged : (Float) -> Unit by rememberUpdatedState(onRatingChanged)

        // Like the XML RatingBar: the touched x position becomes 0.5, 1, 1.5 ... rounded up to the step size.
        fun ratingAt(x : Float) : Float {
            if (starsWidth <= 0) return 0f
            val stars : Float = (x / starsWidth) * starCount
            val stepped : Float = Math.ceil((stars / stepSize).toDouble()).toFloat() * stepSize
            return stepped.coerceIn(0f, starCount.toFloat())
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row (
                modifier = Modifier
                    .onSizeChanged { size : IntSize -> starsWidth = size.width }
                    .pointerInput(Unit) {
                        detectTapGestures { offset : Offset ->
                            val rating : Float = ratingAt(offset.x)
                            // Tapping the current rating again clears it back to 0.
                            currentOnRatingChanged(if (rating == currentRated) 0f else rating)
                        }
                    }
                    .pointerInput(Unit) {
                        // Slide your finger across the stars; dragging past the left edge gives 0.
                        detectHorizontalDragGestures { change : PointerInputChange, _ : Float ->
                            currentOnRatingChanged(ratingAt(change.position.x))
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(starCount) { index : Int ->
                    // How much of this star is filled: 0 (empty), 0.5 (half) or 1 (full).
                    val fill : Float = (rated - index).coerceIn(0f, 1f)
                    Box (
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp)
                    ) {
                        Icon (
                            painter = outlinedStar,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                        if (fill > 0f) {
                            Icon (
                                painter = filledStar,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    // Only draw the left part of the filled star for a half star.
                                    .drawWithContent {
                                        clipRect(right = size.width * fill) {
                                            this@drawWithContent.drawContent()
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SeekBarComposable(progressed : Float, onProgressChanged : (Float) -> Unit) {
        Slider (
            value = progressed,
            onValueChange = { progress : Float -> onProgressChanged(progress) },
            steps = 10,
            valueRange = 0F..1f,
        )
    }

    @Composable
    private fun DiscreteSeekBarComposable(value : Int, valueRange : ClosedFloatingPointRange<Float>, steps : Int, onValueChanged : (Float) -> Unit) {
        Slider (
            value = value.toFloat(),
            onValueChange = { progress : Float -> onValueChanged(progress) },
            steps = steps,
            valueRange = valueRange,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RangeSliderComposable(range : ClosedFloatingPointRange<Float>, valueRange : ClosedFloatingPointRange<Float>, steps : Int, onRangeChanged : (ClosedFloatingPointRange<Float>) -> Unit) {
        RangeSlider (
            value = range,
            onValueChange = { newRange : ClosedFloatingPointRange<Float> -> onRangeChanged(newRange) },
            steps = steps,
            valueRange = valueRange,
        )
    }

    @Preview(showBackground = true)
    @Composable
    private fun MainPreview() {
        JetpackcomposeTheme {
            val isSwitchChecked : Boolean by remember { mutableStateOf(false) }
            val isCheckBoxChecked : Boolean by remember { mutableStateOf(false) }
            val isSpinnerExpanded : Boolean by remember { mutableStateOf(false) }
            val isCustomSpinnerExpanded : Boolean by remember { mutableStateOf(false) }
            val rating : Float by remember { mutableStateOf(0f) }
            val sliderProgress : Float by remember { mutableStateOf(0.0f) }
            SurfaceComposable (
                viewModel = viewModel { MainViewModel() },
                data = "Data",
                progress = 0.0f,
                isSwitchChecked = isSwitchChecked,
                isToggleButtonChecked = false,
                selectedRadioButton = "",
                isCheckBoxChecked = isCheckBoxChecked,
                isSpinnerExpanded = isSpinnerExpanded,
                isCustomSpinnerExpanded = isCustomSpinnerExpanded,
                rate = rating,
                sliderProgress = sliderProgress,
                discreteSliderValue = 0,
                rangeSliderValue = 1f..10f
            )
        }
    }
}