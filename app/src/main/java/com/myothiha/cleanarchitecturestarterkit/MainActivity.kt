package com.myothiha.cleanarchitecturestarterkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.movies.MoviesScreen
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.movies.MoviesViewModel
import com.myothiha.cleanarchitecturestarterkit.ui.theme.CleanArchitectureStarterKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureStarterKitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MoviesViewModel = hiltViewModel()
                    MoviesScreen(uiState = viewModel.uiState, uiEvent = viewModel::onEvent)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CleanArchitectureStarterKitTheme {
        Greeting("Android")
    }
}