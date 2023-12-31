package com.example.jetpackcomponentsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jetpackcomponentsapp.ui.theme.JetpackcomponentsappTheme

class MainActivity : ComponentActivity() {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
    }

    private val labelWeight : Float = 0.16f
    private val inputWeight : Float = 0.29f
    private val buttonWeight : Float = 0.22f
    private val valueWeight : Float = 0.30f
    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackcomponentsappTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (
                        verticalArrangement = Arrangement.SpaceAround,
                        content = {
                            BooleanRowComposable()
                            StringRowComposable()
                            IntegerRowComposable()
                            DoubleRowComposable()
                            LongRowComposable()
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun SwitchComposable(modifier : Modifier, isCheck : Boolean, onCheckedChange : (Boolean) -> Unit) {
        Switch (
            modifier = modifier,
            checked = isCheck,
            onCheckedChange = { isChecked : Boolean -> onCheckedChange(isChecked) },
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditTextComposable(label : String? = null, text : TextFieldValue, onTextChange : (value : TextFieldValue) -> Unit) {
        if (label?.isNotBlank() == true) {
            TextField (
                value = text,
                onValueChange = { value : TextFieldValue -> onTextChange(value) },
                label = { Text(label) },
                maxLines = 1,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                singleLine = true,
            )
        } else {
            TextField (
                value = text,
                onValueChange = { value : TextFieldValue -> onTextChange(value) },
                maxLines = 1,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                singleLine = true,
            )
        }
    }

    @Composable
    private fun BooleanRowComposable() {
        val isSwitchChecked by viewModel.getSwitchChecked().observeAsState(false)
        val value : Boolean? by viewModel.observeBoolean().observeAsState()
        Row (modifier = Modifier.fillMaxWidth(),) {
            Text (
                modifier = Modifier.weight(labelWeight),
                maxLines = 1,
                text = getString(R.string._boolean)
            )
            SwitchComposable (
                modifier = Modifier.weight(inputWeight),
                isCheck = isSwitchChecked,
                onCheckedChange = { isChecked : Boolean ->
                    viewModel.setSwitchChecked(isChecked)
                }
            )
            Button (
                modifier = Modifier.weight(buttonWeight),
                onClick = { viewModel.update(isSwitchChecked) }
            ) {
                Text(text = getString(R.string.update))
            }

            Box (
                modifier = Modifier.weight(valueWeight),
                content = {
                    Text (
                        text = value.toString(),
                    )
                },
                contentAlignment = Alignment.Center,
            )
        }
    }

    @Composable
    private fun StringRowComposable() {
        val textField : TextFieldValue by remember { viewModel.stringTextState }
        val value : String? by viewModel.observeString().observeAsState()
        Row (modifier = Modifier.fillMaxWidth(),) {
            Text (
                modifier = Modifier.weight(labelWeight),
                maxLines = 1,
                text = getString(R.string._string)
            )
            Box (
                modifier = Modifier.weight(inputWeight),
            ) {
                EditTextComposable (
                    text = textField,
                    onTextChange = { value : TextFieldValue ->
                        viewModel.onStringTextChanged(value)
                    }
                )
            }
            Button (
                modifier = Modifier.weight(buttonWeight),
                onClick = { viewModel.update(textField.text.toString()) }
            ) {
                Text(text = getString(R.string.update))
            }
            Text (
                modifier = Modifier.weight(valueWeight),
                text = value.toString(),
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun IntegerRowComposable() {
        val textField : TextFieldValue by remember { viewModel.integerTextState }
        val value : Int? by viewModel.observeInt().observeAsState()
        Row (modifier = Modifier.fillMaxWidth(),) {
            Text (
                modifier = Modifier.weight(labelWeight),
                maxLines = 1,
                text = getString(R.string._integer)
            )
            Box (
                modifier = Modifier.weight(inputWeight),
            ) {
                EditTextComposable (
                    text = textField,
                    onTextChange = { value : TextFieldValue ->
                        viewModel.onIntegerTextChanged(value)
                    }
                )
            }
            Button (
                modifier = Modifier.weight(buttonWeight),
                onClick = { viewModel.update(textField.text.toString().toIntOrNull()) }
            ) {
                Text(text = getString(R.string.update))
            }
            Box (
                modifier = Modifier.weight(valueWeight),
                content = {
                    Text (
                        text = value.toString(),
                    )
                },
                contentAlignment = Alignment.Center,
            )
        }
    }

    @Composable
    private fun DoubleRowComposable() {
        val textField : TextFieldValue by remember { viewModel.doubleTextState }
        val value : Double? by viewModel.observeDouble().observeAsState()
        Row (modifier = Modifier.fillMaxWidth(),) {
            Text (
                modifier = Modifier.weight(labelWeight),
                maxLines = 1,
                text = getString(R.string._double)
            )
            Box (
                modifier = Modifier.weight(inputWeight),
            ) {
                EditTextComposable (
                    text = textField,
                    onTextChange = { value : TextFieldValue ->
                        viewModel.onDoubleTextChanged(value)
                    }
                )
            }
            Button (
                modifier = Modifier.weight(buttonWeight),
                onClick = { viewModel.update(textField.text.toString().toDoubleOrNull()) }
            ) {
                Text(text = getString(R.string.update))
            }
            Box (
                modifier = Modifier.weight(valueWeight),
                content = {
                    Text (
                        text = value.toString(),
                    )
                },
                contentAlignment = Alignment.Center,
            )
        }
    }

    @Composable
    private fun LongRowComposable() {
        val textField : TextFieldValue by remember { viewModel.longTextState }
        val value : Long? by viewModel.observeLong().observeAsState()
        Row (modifier = Modifier.fillMaxWidth(),) {
            Text (
                modifier = Modifier.weight(labelWeight),
                maxLines = 1,
                text = getString(R.string._long)
            )
            Box (
                modifier = Modifier.weight(inputWeight),
            ) {
                EditTextComposable (
                    text = textField,
                    onTextChange = { value : TextFieldValue ->
                        viewModel.onLongTextChanged(value)
                    }
                )
            }
            Button (
                modifier = Modifier.weight(buttonWeight),
                onClick = { viewModel.update(textField.text.toString().toLongOrNull()) }
            ) {
                Text(text = getString(R.string.update))
            }
            Box (
                modifier = Modifier.weight(valueWeight),
                content = {
                    Text (
                        text = value.toString(),
                    )
                },
                contentAlignment = Alignment.Center,
            )
        }
    }
    /*
    @Composable
    fun TableComposable() {
        val tableData = (0..5).mapIndexed { index, item ->
            index to "Item $index"
        }
        LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
            item {
                Row(Modifier.background(Color.Gray)) {
                    TableCell(text = "Column 1", weight = 0.25f)
                    TableCell(text = "Column 2", weight = 0.25f)
                    TableCell(text = "Column 2", weight = 0.25f)
                    TableCell(text = "Column 2", weight = 0.25f)
                }
            }
            items(tableData) {
                val (id, text) = it
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = id.toString(), weight = 0.25f)
                    TableCell(text = text, weight = 0.25f)
                    TableCell(text = "Button", weight = 0.25f)
                    TableCell(text = "Value", weight = 0.25f)
                }
            }
        }
    }

    @Composable
    fun RowScope.TableCell (
        text : String,
        weight : Float
    ) {
        Text (
            text = text,
            Modifier
                .border(1.dp, Color.Black)
                .weight(weight)
                .padding(8.dp)
        )
    }
    */
}