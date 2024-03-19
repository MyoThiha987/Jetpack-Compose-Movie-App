package com.myothiha.cleanarchitecturestarterkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout.AccountViewModel
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout.ScreenUiState
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.home.MainScreen
import com.myothiha.cleanarchitecturestarterkit.ui.theme.CleanArchitectureStarterKitTheme
import com.myothiha.domain.model.Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.uiState.isLoading
        }
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val uiState = viewModel.uiState
            val systemUiController = rememberSystemUiController()
            val darkTheme = shouldUseDarkTheme(uiState = uiState)

            LaunchedEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
            }
            CleanArchitectureStarterKitTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(uiState: ScreenUiState): Boolean {
    if (uiState.isLoading) return isSystemInDarkTheme()
    return when (uiState.theme) {
        Theme.SYSTEM -> isSystemInDarkTheme()
        Theme.LIGHT -> false
        Theme.DARK -> true
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.fillMaxWidth()
    )
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CleanArchitectureStarterKitTheme {
        Greeting("Android")
    }
}*/

