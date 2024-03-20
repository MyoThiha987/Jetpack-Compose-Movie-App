package com.myothiha.domain.usecases

import com.myothiha.domain.repository.AppLanguageRepository
import com.myothiha.domain.utils.coroutine.CoroutineUseCase
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 20/03/2024 at 4:41 PM.
 **/

class SaveAppLanguageUseCase @Inject constructor(
    private val repository: AppLanguageRepository,
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<String, Unit>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: String) {
        return repository.saveSelectedLanguage(language = params)
    }
}