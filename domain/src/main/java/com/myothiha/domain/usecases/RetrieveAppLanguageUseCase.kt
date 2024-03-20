package com.myothiha.domain.usecases

import com.myothiha.domain.repository.AppLanguageRepository
import com.myothiha.domain.utils.coroutine.CoroutineUseCase
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import com.myothiha.domain.utils.coroutine.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 20/03/2024 at 4:41 PM.
 **/

class RetrieveAppLanguageUseCase @Inject constructor(
    private val repository: AppLanguageRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, String>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Unit): Flow<String> {
        return repository.retrieveSelectedLanguage
    }
}