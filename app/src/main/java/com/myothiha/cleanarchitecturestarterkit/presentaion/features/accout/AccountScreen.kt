package com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.RadioButtonText
import com.myothiha.domain.model.Theme

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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            RadioButtonText(
                textRes = R.string.theme_system,
                isSelected = uiState.theme == Theme.SYSTEM,
                onClick = { uiEvent(ScreenUiEvent.onSelectTheme(Theme.SYSTEM)) }
            )
            RadioButtonText(
                textRes = R.string.theme_light,
                isSelected = uiState.theme == Theme.LIGHT,
                onClick = { uiEvent(ScreenUiEvent.onSelectTheme(Theme.LIGHT)) }
            )
            RadioButtonText(
                textRes = R.string.theme_dark,
                isSelected = uiState.theme == Theme.DARK,
                onClick = { uiEvent(ScreenUiEvent.onSelectTheme(Theme.DARK)) }
            )
        }
    }

}