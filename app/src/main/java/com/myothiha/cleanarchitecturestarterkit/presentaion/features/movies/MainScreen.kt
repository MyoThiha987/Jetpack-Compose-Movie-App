package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movies

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail.DetailScreen

/**
 * @Author myothiha
 * Created 10/03/2024 at 1:57 AM.
 **/

@Composable
fun MainScreen(navController : NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val movieDestinations = listOf("Home", "Detail")
    /*val currentScreen = movieDestinations.find {
        backStackEntry?.destination?.route == it ||
                backStackEntry?.destination?.route == it
    } ?: "Home"*/
    Scaffold(
        topBar = {}
    ) { innerPaddings ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(innerPaddings),
            startDestination = "home"
        ) {
            composable("home") {
                val moviesViewModel: MoviesViewModel = hiltViewModel()
                MoviesScreen(
                    uiState = moviesViewModel.uiState,
                    uiEvent = moviesViewModel::onEvent,
                    navController = navController
                )
            }
            composable("detail") {
                //val moviesViewModel: MoviesViewModel = hiltViewModel()
                DetailScreen(
                    navController = navController

                )
            }
        }
    }

}
