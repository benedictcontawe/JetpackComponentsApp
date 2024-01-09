package com.example.jetpackcomponentsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomponentsapp.ui.theme.JetpackcomponentsappTheme

public class MainActivity : ComponentActivity() {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
    }

    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController : NavHostController = rememberNavController()
            JetpackcomponentsappTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column () {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            content = {
                                Button (
                                    onClick = {
                                        navController.navigate(Constants.ROUTE_HORIZONTAL)
                                    },
                                    content = {
                                        Text(text = getString(R.string.horizontal))
                                    }
                                )
                                Button (
                                    onClick = {
                                        navController.navigate(Constants.ROUTE_VERTICAL)
                                    },
                                    content = {
                                        Text(text = getString(R.string.vertical))
                                    }
                                )
                            }
                        )
                        NavHostComposable(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun NavHostComposable(navController : NavHostController) {
        NavHost(navController = navController, startDestination  = Constants.ROUTE_HORIZONTAL) {
            composable(route = Constants.ROUTE_HORIZONTAL) { backStackEntry : NavBackStackEntry ->
                HorizontalPagerComposable()
            }
            composable(route = Constants.ROUTE_VERTICAL) { backStackEntry : NavBackStackEntry ->
                VerticalPagerComposable()
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun HorizontalPagerComposable() {
        val pagerState : PagerState = rememberPagerState(initialPage = viewModel.horizontalPage, pageCount = { viewModel.getListCount() } )
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                viewModel.horizontalPage = page
            }
        }
        HorizontalPager (
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            pageContent = { page : Int ->
                if (viewModel.isIconNil(page))
                    DefaultCellComposable (
                        text = getString(R.string.horizontal_page_, viewModel.getName(page))
                    )
                else IconCellComposable (
                    text = getString(R.string.horizontal_page_, viewModel.getName(page)),
                    icon = viewModel.getIcon(page = page)!!
                )
            }
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun VerticalPagerComposable() {
        val pagerState : PagerState = rememberPagerState(initialPage = viewModel.vertivalPage, pageCount = { viewModel.getListCount() } )
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                viewModel.vertivalPage = page
            }
        }
        //pagerState.scrollToPage(0)
        //pagerState.animateScrollToPage(0)
        VerticalPager (
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            pageContent = { page : Int ->
                if (viewModel.isIconNil(page))
                    DefaultCellComposable (
                        text = getString(R.string.vertical_page_, viewModel.getName(page))
                    )
                else IconCellComposable (
                    text = getString(R.string.vertical_page_, viewModel.getName(page)),
                    icon = viewModel.getIcon(page = page)!!
                )
            }
        )
    }

    @Composable
    private fun IconComposable(icon : Int) {
        Icon (
            contentDescription = null,
            painter  = painterResource(id = icon)
        )
    }

    @Composable
    private fun DefaultCellComposable(text : String) {
        Box (
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        ) {
            Text (
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = TextStyle (
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 40.sp
                )
            )
        }
    }

    @Composable
    private fun IconCellComposable(text : String, icon : Int) {
        Column (
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconComposable(icon = icon)
            Text (
                text = text,
                style = TextStyle (
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 40.sp
                )
            )
        }
    }
}