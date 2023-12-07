package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.ui.theme.JetpackcomposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackcomposeTheme {
                val viewModel : MainViewModel = viewModel { MainViewModel() }
                val data by viewModel.getData().observeAsState("Result")
                val progress by viewModel.getProgressData().observeAsState(0.0f)
                val isSwitchChecked by viewModel.getSwitchChecked().observeAsState(false) //var isSwitchChecked by remember { mutableStateOf(false) }
                val isCheckBoxChecked by viewModel.getCheckBoxChecked().observeAsState(false) //var isCheckBoxChecked by remember { mutableStateOf(false) }
                val isSpinnerExpanded by viewModel.getSpinnerExpanded().observeAsState(false) //var isSpinnerExpanded by remember { mutableStateOf(false) }
                val isCustomSpinnerExpanded by viewModel.getCustomSpinnerExpanded().observeAsState(false) //var isSpinnerExpanded by remember { mutableStateOf(false) }
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
                        Button (
                            onClick = { viewModel.setData("Button Was Clicked") }
                        ) {
                            Text(text = "Send Data")
                        }
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Switch (
                            checked = isSwitchChecked,
                            onCheckedChange = { isChecked : Boolean -> viewModel.setSwitchChecked(isChecked) },
                            /*
                            colors = SwitchDefaults.colors (
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                uncheckedThumbColor = Color.Gray
                            )
                            */
                        )
                        Text(text = "Toggle Button Under Construction")
                        RadioGroupComposable (
                            list = viewModel.onOffList,
                            onSelect = { option -> viewModel.isRadioButtonChecked(option) },
                            onClick = { option -> viewModel.setSelectedRadioButton(option) }
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
                            onItemSelected = { spinner ->
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
                            onItemSelected = { spinner ->
                                viewModel.setCustomSpinnerExpanded(false)
                                viewModel.setCustomSpinnerSelectedIndex(spinner)
                            }
                        )
                        Text(text = "Rating Bar Under Construction")
                        Text(text = "Seek Bar Under Construction")
                        Text(text = "Discrete Seek Bar Under Construction")
                    }
                }
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
                        onClick = {
                            onClick(option)
                        },
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
                list.forEach { spinner ->
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
                modifier = Modifier
                    .height(56.dp)
                    .clickable { onClick() }
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
                list.forEach { spinner ->
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

    @Preview(showBackground = true)
    @Composable
    private fun GreetingPreview() {
        JetpackcomposeTheme {
            Text(
                text = "Hello Android!",
            )
        }
    }
}