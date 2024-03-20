package com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.RadioButtonText
import com.myothiha.domain.model.Theme
import kotlinx.coroutines.launch

/**
 * @Author myothiha
 * Created 13/03/2024 at 5:47 PM.
 **/

@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel,
    paddingValues: PaddingValues
) {

    val uiState = viewModel.uiState
    val uiEvent = viewModel::onEvent

    AccountScreen(
        navController = navController,
        uiState = uiState,
        uiEvent = uiEvent,
        paddingValues = paddingValues
    )
}

@Composable
fun AccountScreen(
    navController: NavController,
    uiState: ScreenUiState,
    uiEvent: (ScreenUiEvent) -> Unit,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            RadioButtonText(
                textRes = R.string.theme_system,
                isSelected = uiState.data.theme == Theme.SYSTEM,
                onClick = { uiEvent(ScreenUiEvent.onSelectTheme(Theme.SYSTEM)) }
            )
            RadioButtonText(
                textRes = R.string.theme_light,
                isSelected = uiState.data.theme == Theme.LIGHT,
                onClick = { uiEvent(ScreenUiEvent.onSelectTheme(Theme.LIGHT)) }
            )
            RadioButtonText(
                textRes = R.string.theme_dark,
                isSelected = uiState.data.theme == Theme.DARK,
                onClick = { uiEvent(ScreenUiEvent.onSelectTheme(Theme.DARK)) }
            )
        }
        item {
            RadioButtonText(
                textRes = R.string.language_en,
                isSelected = uiState.data.language == "en",
                onClick = {
                    scope.launch {
                        uiEvent(
                            ScreenUiEvent.onSelectLanguage(
                                context = context,
                                language = "en"
                            )
                        )
                        context.restartActivity()
                    }


                }
            )
            RadioButtonText(
                textRes = R.string.language_myanmar,
                isSelected = uiState.data.language == "my",
                onClick = {
                    scope.launch {
                        uiEvent(
                            ScreenUiEvent.onSelectLanguage(
                                context = context,
                                language = "my"
                            )
                        )
                        context.restartActivity()
                    }
                }

            )

        }
    }

}

fun Context.restartActivity() {
    val intent = Intent(this, com.myothiha.cleanarchitecturestarterkit.MainActivity::class.java)
    intent.flags =
        android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK or android.content.Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(intent)
}