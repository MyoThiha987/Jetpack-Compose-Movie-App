package com.myothiha.domain.usecases

import com.myothiha.domain.model.Theme
import com.myothiha.domain.repository.AppThemeRepository
import com.myothiha.domain.utils.coroutine.CoroutineUseCase
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 19/03/2024 at 6:07 PM.
 **/
class AppThemeUseCase @Inject constructor(
    private val repository: AppThemeRepository,
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Theme, Unit>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Theme) {
        return repository.setAppTheme(theme = params)
    }
}