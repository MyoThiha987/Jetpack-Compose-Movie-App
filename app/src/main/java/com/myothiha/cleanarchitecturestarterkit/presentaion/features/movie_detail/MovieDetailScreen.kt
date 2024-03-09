package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:42 PM.
 **/

@Composable
fun DetailScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(modifier = Modifier.align(alignment = Alignment.Center),onClick = { navController.popBackStack() }) {

        }
    }
}