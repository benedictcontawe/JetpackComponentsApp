package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.jetpackcompose.ui.theme.JetpackcomposeTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class MainActivity : ComponentActivity() {

    companion object {
        private val TAG = MainActivity::class.java.getSimpleName()
    }

    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val isLoading : Boolean by viewModel.observeLoading().collectAsState(initial = false)
            val swipeRefreshState : SwipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
            val list : List<NasaHolderModel> by viewModel.observeAPOD().observeAsState(listOf<NasaHolderModel>())
            JetpackcomposeTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SwipeRefresh (
                        state = swipeRefreshState,
                        //onRefresh = viewModel::requestAPOD,
                        onRefresh = { viewModel.requestAPOD() },
                    ) {
                        LazyColumn (
                            //modifier = Modifier.pullRefresh(pullRefreshState).verticalScroll(rememberScrollState()),
                            userScrollEnabled = true
                        ) {
                            items (
                                items = list,
                                itemContent = { model -> NasaCellComposable(model) }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestAPOD()
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    private fun ImageComposable(image : String) {
        GlideImage (
            model = image,
            contentDescription = null,
            loading = placeholder(R.drawable.animation_loading),
            failure = placeholder(R.drawable.ic_image)
        )
    }

    @Composable
    private fun NasaCellComposable(model : NasaHolderModel) {
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
                    modifier = Modifier.weight(0.20f),
                    contentAlignment = Alignment.Center
                ) {
                    ImageComposable(image = model.image)
                }
                Box (
                    modifier = Modifier.weight(0.60f),
                    contentAlignment = Alignment.Center
                ) {
                    Column ( modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth() ) {
                        Text (
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = typography.labelLarge,
                            text = model.title,
                        )
                        Text (
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = typography.bodySmall,
                            text = model.explanation,
                        )
                    }
                }
                Box (
                    modifier = Modifier.weight(0.20f),
                    contentAlignment = Alignment.Center
                ) {
                    Column ( modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth() ) {
                        Text (
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = typography.labelLarge,
                            text = model.date
                        )
                        Text (
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = typography.bodySmall,
                            text = model.copyright
                        )
                    }
                }
            }
        }
    }
}