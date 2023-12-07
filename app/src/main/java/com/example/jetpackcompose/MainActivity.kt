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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            viewModel.onOffList.forEach { option ->
                                Row (
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    RadioButton (
                                        selected = viewModel.isRadioButtonChecked(option),
                                        onClick = {
                                            viewModel.setSelectedRadioButton(option)
                                        },
                                        enabled = true,
                                        //colors = RadioButtonDefaults.colors (selectedColor = Color.Magenta)
                                    )
                                    Text(text = option, textAlign = TextAlign.Center, fontSize = 13.sp)
                                }
                            }
                        }
                        Row (
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Checkbox (
                                checked = isCheckBoxChecked,
                                onCheckedChange = { isChecked : Boolean -> viewModel.setCheckBoxChecked(isChecked)}
                            )
                            Text(text = viewModel.getCheckBoxText(isCheckBoxChecked), textAlign = TextAlign.Center, fontSize = 13.sp)
                        }
                        Column() {
                            Box (
                                modifier = Modifier.height(56.dp) //.background(Color.Gray)
                                    .clickable { viewModel.setSpinnerExpanded(isSpinnerExpanded.not()) }
                            ) {
                                Row (
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(viewModel.getSelectedSpinner())
                                    Icon (
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = null
                                    )
                                }
                            }
                            DropdownMenu (
                                expanded = isSpinnerExpanded,
                                onDismissRequest = { viewModel.setSpinnerExpanded(false) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                viewModel.spinnerList.forEach { spinner ->
                                    DropdownMenuItem( onClick = {
                                        viewModel.setSpinnerExpanded(false)
                                        viewModel.setSpinnerSelectedIndex(spinner)
                                    }, text = { Text(spinner) } )
                                }
                            }
                        }
                        Text(text = "Custom Spinner Under Construction")
                        /*
                        TextField (
                          readOnly = true,
                          value = "",
                          onValueChange = { },
                          label = { "Categories" },
                          trailingIcon = {
                              ExposedDropdownMenuDefaults.TrailingIcon(
                                  expanded = false
                              )
                          },
                          //colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        */
                        Text(text = "Rating Bar Under Construction")
                        Text(text = "Seek Bar Under Construction")
                        Text(text = "Discrete Seek Bar Under Construction")
                    }
                }
            }
        }
    }
}
/*
@Composable
fun SpacerWithAspectRatio(aspectRatio: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .background(Color.Gray)
    ) {
        // You can add content inside the Box if needed
    }
}

@Composable
fun HorizontalProgressBar() {
    var progress by remember { mutableStateOf(0.5f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to simulate progress change
        Button(onClick = {
            // Simulate progress change
            progress = if (progress < 1.0f) progress + 0.1f else 0.0f
        }) {
            Text(text = "Increase Progress")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackcomposeTheme {
        Greeting("Android")
    }
}
*/