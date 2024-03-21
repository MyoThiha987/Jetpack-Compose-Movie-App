package com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.ProfileView
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.SettingItemView
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.SettingView

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController,
    uiState: ScreenUiState,
    uiEvent: (ScreenUiEvent) -> Unit,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.lbl_setting)) })
        },
        contentColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = it,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ProfileView()
                Spacer(modifier = Modifier.height(24.dp))
                SettingView(title = R.string.lbl_general,
                    content = {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            SettingItemView(
                                title = R.string.lbl_theme,
                                subtitle = "",
                                isShowSubtitle = false,
                                headerIcon = R.drawable.ic_theme,
                                tailIcon = R.drawable.ic_arrow_right
                            )
                            SettingItemView(
                                title = R.string.lbl_language,
                                subtitle = uiState.data.language,
                                isShowSubtitle = true,
                                headerIcon = R.drawable.ic_language,
                                tailIcon = R.drawable.ic_arrow_right
                            )
                        }
                    })
                Spacer(modifier = Modifier.height(24.dp))
                SettingView(title = R.string.lbl_security,
                    content = {
                        SettingItemView(
                            title = R.string.lbl_fingerprint,
                            subtitle = uiState.data.language,
                            isShowSubtitle = false,
                            headerIcon = R.drawable.ic_fingerprint,
                            tailIcon = R.drawable.ic_arrow_right
                        )
                    })
            }
            /*item {
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

            }*/
        }
    }


}

fun Context.restartActivity() {
    val intent = Intent(this, com.myothiha.cleanarchitecturestarterkit.MainActivity::class.java)
    intent.flags =
        android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK or android.content.Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(intent)
}

@Composable
@Preview(showBackground = true)
fun AccountScreenPreview() {
    AccountScreen(
        navController = rememberNavController(),
        uiState = ScreenUiState(),
        uiEvent = {},
        paddingValues = PaddingValues(16.dp)
    )
}