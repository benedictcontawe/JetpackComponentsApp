package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Row (modifier = Modifier.fillMaxWidth(),) {
                            Box (
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = data, textAlign = TextAlign.Center, fontSize = 23.sp)
                            }
                            Box (modifier = Modifier
                                .weight(1f)
                                .background(Color.Red)
                            ) {
                                LinearProgressIndicator (
                                    progress = progress,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(13.dp)
                                )
                            }
                        }
                        //Spacer(modifier = Modifier.height(0.dp))
                        Button(onClick = { viewModel.setData("Button Was Clicked")
                        }) { Text(text = "Send Data") }
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Switch (
                            checked = isSwitchChecked,
                            onCheckedChange = { isChecked -> viewModel.setSwitchChecked(isChecked) },
                            /*
                            colors = SwitchDefaults.colors (
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                uncheckedThumbColor = Color.Gray
                            )
                            */
                        )
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Text(text = "Toggle Button")
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Text(text = "Radio Group, Radio Button")
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Text(text = "Check Box")
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Text(text = "Spinner")
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Text(text = "Custom Spinner")
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Text(text = "Rating Bar")
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Text(text = "Seek Bar")
                        //Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f / 0.013f, true))
                        Text(text = "Discrete Seek Bar")
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