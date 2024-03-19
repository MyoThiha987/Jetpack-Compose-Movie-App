package com.myothiha.domain.repository

import com.myothiha.domain.model.Theme
import com.myothiha.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * @Author myothiha
 * Created 19/03/2024 at 5:19 PM.
 **/
interface AppThemeRepository {
    val userData: Flow<User>
    suspend fun setAppTheme(theme: Theme)
}