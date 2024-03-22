package com.myothiha.cleanarchitecturestarterkit.presentaion.features.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.myothiha.cleanarchitecturestarterkit.navigation.AppNavigation
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.BottomNavigationBar

/**
 * @Author myothiha
 * Created 10/03/2024 at 1:57 AM.
 **/

@Composable
fun MainScreen(navController: NavHostController) {

    Scaffold(
        topBar = {},
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPaddings ->

        AppNavigation(navHostController = navController, paddingValues = innerPaddings)

    }

}
