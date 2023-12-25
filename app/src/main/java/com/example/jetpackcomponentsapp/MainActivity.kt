package com.example.jetpackcomponentsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.ui.theme.JetpackComponentsAppTheme

class MainActivity : ComponentActivity() {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
        public fun newIntent(context : Context) : Intent = Intent(context.applicationContext, MainActivity::class.java)
    }

    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val context : Context = LocalContext.current
            val isLoading : Boolean by viewModel.observeLoadState().observeAsState(initial = false)
            val isAddDialogShow : Boolean by viewModel.observeAddDialog().observeAsState(initial = false)
            val isUpdateDialogShow : Boolean by viewModel.observeUpdateDialog().collectAsState(initial = false)
            JetpackComponentsAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScaffoldComposable()
                    if (isAddDialogShow) {
                        AddDialogComposable (
                            onDismissRequest = {
                                viewModel.setAddDialog(false)
                           },
                            dismissOnBackPress = true,
                            dismissOnClickOutside = false
                        )
                    }
                    if(isUpdateDialogShow) {
                        UpdateDialogComposable (
                            onDismissRequest = {
                                viewModel.setUpdateDialog(false)
                            },
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true,
                        )
                    }
                    if (isLoading) {
                        ProgressDialogComposable()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScaffoldComposable() {
        val navController : NavHostController = rememberNavController()
        Scaffold (
            content = { paddingValues ->
                Surface (
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHostComposable(navController = navController)
                }
            },
            floatingActionButton = {
                Row (
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    FloatingActionButtonComposable (
                        icon = R.drawable.ic_add_white,
                        onClick = {
                            viewModel.onAddTextChanged()
                            viewModel.setAddDialog(true)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    FloatingActionButtonComposable (
                        icon = R.drawable.ic_delete_white,
                        onClick = {
                            viewModel.deleteAll()
                        }
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
        )
    }

    @Composable
    private fun NavHostComposable(navController : NavHostController) {
        NavHost(navController = navController, startDestination  = Constants.ROUTE_MAIN) {
            composable(route = Constants.ROUTE_MAIN) { backStackEntry : NavBackStackEntry ->
                val list : List<CustomModel> by viewModel.observeItems().observeAsState(listOf<CustomModel>())
                /*
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text (
                        text = stringResource( id = R.string.app_name ),
                        textAlign = TextAlign.Center,
                    )
                }
                */
                LazyColumn (
                    userScrollEnabled = true
                ) {
                    items (
                        items = list,
                        itemContent = { model -> CellComposable(model) }
                    )
                }
            }
        }
    }

    @Composable
    private fun CellComposable(model : CustomModel) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 0.25f),
            elevation = CardDefaults.cardElevation (
                defaultElevation = 3.dp,
                pressedElevation = 0.dp,
                focusedElevation = 1.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 0.dp
            ),
            shape = CardDefaults.shape,
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Box (
                    modifier = Modifier.weight(0.15f),
                    contentAlignment = Alignment.Center
                ) {
                    ImageComposable(icon = model.icon)
                }
                Box (
                    modifier = Modifier.weight(0.60f),
                    contentAlignment = Alignment.Center
                ) {
                    Text (
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = typography.labelLarge,
                        text = model.name ?: "Nil",
                    )
                }
                Box (
                    modifier = Modifier.weight(0.25f),
                    contentAlignment = Alignment.Center
                ) {
                    Column ( modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth() ) {
                        Button (
                            onClick = {
                                viewModel.setUpdate(model)
                                viewModel.setUpdateDialog(true)
                            }
                        ) {
                            Text (
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = stringResource(id = R.string.edit)
                            )
                        }
                        Button (
                            onClick = {
                                viewModel.deleteItem(model)
                            }
                        ) {
                            Text (
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = stringResource(id = R.string.delete)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ImageComposable(icon : Int) {
        Icon (
            contentDescription = null,
            painter  = painterResource(id = icon)
        )
    }

    @Composable
    fun FloatingActionButtonComposable (
        icon : Int,
        onClick : () -> Unit
    ) {
        FloatingActionButton (
            onClick = onClick,
            modifier = Modifier.clip(CircleShape)
        ) {
            Icon (
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditTextComposable(label : String, text : TextFieldValue, onTextChange : (value : TextFieldValue) -> Unit) {
        TextField (
            value = text,
            onValueChange = { value : TextFieldValue -> onTextChange(value) },
            label = { Text(label) },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )
    }

    @Composable
    fun AddDialogComposable(onDismissRequest : () -> Unit, dismissOnBackPress : Boolean, dismissOnClickOutside : Boolean) {
        val text : TextFieldValue by remember { viewModel.addTextState }
        Dialog (
            onDismissRequest = onDismissRequest,
            properties = DialogProperties (
                dismissOnBackPress = dismissOnBackPress, dismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            Column (
                modifier = Modifier.fillMaxWidth().background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(0.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box (
                        modifier = Modifier.weight(0.20f),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageComposable(icon = R.drawable.ic_android_black)
                    }
                    Box (
                        modifier = Modifier.weight(0.60f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text (
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = typography.labelLarge,
                            text = stringResource(id = R.string.add_name),
                        )
                    }
                }
                EditTextComposable (
                    label = stringResource(id = R.string.add_name),
                    text = text,
                    onTextChange = { value : TextFieldValue ->
                        viewModel.onAddTextChanged(value)
                    }
                )
                Button (
                    onClick = {
                        viewModel.insertItem()
                        onDismissRequest()
                    }
                ) {
                    Text (
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = typography.labelLarge,
                        text = stringResource(id = R.string.add)
                    )
                }
            }
        }
    }

    @Composable
    fun UpdateDialogComposable(onDismissRequest : () -> Unit, dismissOnBackPress : Boolean, dismissOnClickOutside : Boolean) {
        val text : TextFieldValue by remember { viewModel.updateTextState }
        viewModel.onUpdateTextChanged()
        Dialog (
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            Column (
                modifier = Modifier.fillMaxWidth().background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(0.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box (
                        modifier = Modifier.weight(0.20f),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageComposable(icon = R.drawable.ic_android_black)
                    }
                    Box(
                        modifier = Modifier.weight(0.60f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = typography.labelLarge,
                            text = stringResource(id = R.string.your_name),
                        )
                    }
                }
                EditTextComposable (
                    label = stringResource(id = R.string.update_name),
                    text = text,
                    onTextChange = { value : TextFieldValue ->
                        viewModel.onUpdateTextChanged(value)
                    }
                )
                Button(
                    onClick = {
                        viewModel.updateItem()
                        onDismissRequest()
                    }
                ) {
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = typography.labelLarge,
                        text = stringResource(id = R.string.update_name)
                    )
                }
            }
        }
    }

    @Composable
    fun ProgressDialogComposable() {
        Dialog (
            onDismissRequest = { },
            properties = DialogProperties (
                dismissOnBackPress = false, dismissOnClickOutside = false
            )
        ) {
            Column (
                modifier = Modifier.fillMaxWidth().background (
                    color = MaterialTheme.colorScheme.background,
                ),
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator (
                    color = MaterialTheme.colorScheme.primary,
                    //color = Color.Red,
                    strokeWidth = 5.dp,
                )
                Text (
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.labelLarge,
                    text = stringResource(id = R.string.Processing__please_wait_),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}