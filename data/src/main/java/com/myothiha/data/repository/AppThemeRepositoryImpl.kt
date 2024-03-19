package com.myothiha.data.repository

import android.content.Context
import com.myothiha.cleanarchitecturestarterkit.data.ThemeProto
import com.myothiha.data.cache.userProtoDatStore
import com.myothiha.domain.model.Theme
import com.myothiha.domain.model.User
import com.myothiha.domain.repository.AppThemeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 19/03/2024 at 5:19 PM.
 **/
class AppThemeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context

) : AppThemeRepository {
    override val userData: Flow<User>
        get() = context.userProtoDatStore.data.map {
            User(theme = it.theme.asDarkTheme())
        }

    override suspend fun setAppTheme(theme: Theme) {
        context.userProtoDatStore.updateData {
            it.toBuilder().setTheme(theme.asDarkThemeProto()).build()
        }
    }
}

internal fun Theme.asDarkThemeProto() = when (this) {
    Theme.SYSTEM -> ThemeProto.FOLLOW_SYSTEM
    Theme.LIGHT -> ThemeProto.LIGHT
    Theme.DARK -> ThemeProto.DARK
}

internal fun ThemeProto.asDarkTheme() = when (this) {
    ThemeProto.UNRECOGNIZED,
    ThemeProto.FOLLOW_SYSTEM -> Theme.SYSTEM

    ThemeProto.LIGHT -> Theme.LIGHT
    ThemeProto.DARK -> Theme.DARK
}