package com.myothiha.domain.usecases

import com.myothiha.domain.model.User
import com.myothiha.domain.repository.AppThemeRepository
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import com.myothiha.domain.utils.coroutine.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 19/03/2024 at 6:12 PM.
 **/

class UserDataUseCase @Inject constructor(
    private val repository: AppThemeRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, User>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Unit): Flow<User> {
        return repository.userData
    }
}