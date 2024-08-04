package com.myothiha.cleanarchitecturestarterkit.presentaion.features.home

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import com.myothiha.cleanarchitecturestarterkit.navigation.AppNavigation
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout.ScreenUiState
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.BottomNavigationBar

/**
 * @Author myothiha
 * Created 10/03/2024 at 1:57 AM.
 **/

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: ScreenUiState
) {
    Scaffold(
        topBar = {},
        bottomBar = {
            BottomNavigationBar(
                uiState =uiState,
                navController = navController
            )
        }
    ) { innerPaddings ->

        AppNavigation(navHostController = navController, paddingValues = innerPaddings)

    }


}
