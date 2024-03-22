package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.domain.model.Theme

/**
 * @Author myothiha
 * Created 22/03/2024 at 8:42 PM.
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
        windowInsets = WindowInsets(0, 0, 0, 0),
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        content()
    }
}

@Composable
fun LanguageList(
    language: String ?= "en",
    onSelectLanguage: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(bottom = 16.dp)) {
        item {
            RadioButtonText(
                textRes = R.string.language_en,
                isSelected = language == "en",
                onClick = { onSelectLanguage("en") }
            )
            RadioButtonText(
                textRes = R.string.language_myanmar,
                isSelected = language == "my",
                onClick = {
                    onSelectLanguage("my")
                }

            )
        }
    }
}

@Composable
fun ThemeList(
    theme: Theme,
    onSelectTheme: (Theme) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(bottom = 16.dp)) {
        item {
            RadioButtonText(
                textRes = R.string.theme_system,
                isSelected = theme == Theme.SYSTEM,
                onClick = { onSelectTheme(Theme.SYSTEM) }
            )
            RadioButtonText(
                textRes = R.string.theme_light,
                isSelected = theme == Theme.LIGHT,
                onClick = { onSelectTheme(Theme.LIGHT) }
            )
            RadioButtonText(
                textRes = R.string.theme_dark,
                isSelected = theme == Theme.DARK,
                onClick = { onSelectTheme(Theme.DARK) }
            )
        }
    }
}
