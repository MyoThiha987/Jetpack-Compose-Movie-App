package com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewModelScope
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.Theme
import com.myothiha.domain.usecases.AppThemeUseCase
import com.myothiha.domain.usecases.RetrieveAppLanguageUseCase
import com.myothiha.domain.usecases.SaveAppLanguageUseCase
import com.myothiha.domain.usecases.UserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 19/03/2024 at 3:12 PM.
 **/


@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userDataUse: UserDataUseCase,
    private val themeUseCase: AppThemeUseCase,
    private val saveAppLanguageUseCase: SaveAppLanguageUseCase,
    private val retrieveAppLanguageUseCase: RetrieveAppLanguageUseCase
) : BaseViewModel() {

    var uiState by mutableStateOf(ScreenUiState())
        private set

    init {
        getUserData()
    }

    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.onSelectTheme -> setAppTheme(theme = event.theme)
            is ScreenUiEvent.onSelectLanguage -> saveSelectedLanguage(
                context = event.context,
                language = event.language
            )
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val themeFlow = userDataUse.execute(params = Unit)
            val languageFlow = retrieveAppLanguageUseCase.execute(params = Unit)
            val result = themeFlow.combine(languageFlow, transform = { user, language ->
                UserUiData(theme = user.theme, language = language)
            })
            result.collectLatest {
                uiState = uiState.copy(isLoading = false, data = it)
            }
        }
    }

    private fun setAppTheme(theme: Theme) {
        viewModelScope.launch {
            themeUseCase.execute(params = theme)
        }
    }

    private fun saveSelectedLanguage(context: Context, language: String) {
        viewModelScope.launch {
            //setLanguage(context = context, language = language)
            //Save Selected Language
            saveAppLanguageUseCase.execute(params = language)
        }
    }


}

data class ScreenUiState(
    var isLoading: Boolean = false,
    val data: UserUiData = UserUiData()
)

sealed class ScreenUiEvent {
    data class onSelectTheme(val theme: Theme) : ScreenUiEvent()
    data class onSelectLanguage(val context: Context, val language: String) : ScreenUiEvent()
}

data class UserUiData(
    val theme: Theme = Theme.SYSTEM,
    val language: String = "en"
)