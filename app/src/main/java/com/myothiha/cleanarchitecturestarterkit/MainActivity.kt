package com.myothiha.cleanarchitecturestarterkit

import android.content.Context
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.uiState.isLoading
        }
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            val uiState = viewModel.uiState
            val systemUiController = rememberSystemUiController()
            val darkTheme = shouldUseDarkTheme(uiState = uiState)

            LaunchedEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
            }

            SetLanguage(context = applicationContext, language = uiState.data.language)

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
fun SetLanguage(context: Context, language: String) {
    context.resources.apply {
        val locale = Locale(language)
        val configuration = LocalConfiguration.current
        configuration.setLocale(locale)
        val resources = LocalContext.current.resources
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}


@Composable
private fun shouldUseDarkTheme(uiState: ScreenUiState): Boolean {
    if (uiState.isLoading) return isSystemInDarkTheme()
    return when (uiState.data.theme) {
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

