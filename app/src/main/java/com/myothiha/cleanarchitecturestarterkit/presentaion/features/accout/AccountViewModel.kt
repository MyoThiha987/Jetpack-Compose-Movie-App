package com.myothiha.cleanarchitecturestarterkit.presentaion.features.accout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.Theme
import com.myothiha.domain.usecases.AppThemeUseCase
import com.myothiha.domain.usecases.UserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 19/03/2024 at 3:12 PM.
 **/


@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userDataUse: UserDataUseCase,
    private val themeUseCase: AppThemeUseCase
) : BaseViewModel() {

    var uiState by mutableStateOf(ScreenUiState())
        private set

    init {
        getUserData()
    }

    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.onSelectTheme -> setAppTheme(theme = event.theme)
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            userDataUse.execute(params = Unit).collectLatest {
                uiState = uiState.copy(
                    isLoading = false,
                    theme = it.theme
                )
            }
        }
    }

    private fun setAppTheme(theme: Theme) {
        viewModelScope.launch {
            themeUseCase.execute(params = theme)
        }
    }
}

data class ScreenUiState(
    var isLoading: Boolean = false,
    val theme: Theme = Theme.SYSTEM
)

sealed class ScreenUiEvent {
    data class onSelectTheme(val theme: Theme) : ScreenUiEvent()
}