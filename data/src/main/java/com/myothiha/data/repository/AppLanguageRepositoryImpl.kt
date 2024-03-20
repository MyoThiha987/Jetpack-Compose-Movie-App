package com.myothiha.data.repository

import android.content.Context
import com.myothiha.data.cache.datastore.languageProtoDatStore
import com.myothiha.domain.repository.AppLanguageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 20/03/2024 at 4:49 PM.
 **/
class AppLanguageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppLanguageRepository {
    override val retrieveSelectedLanguage: Flow<String>
        get() = context.languageProtoDatStore.data.map {
            it.language
        }

    override suspend fun saveSelectedLanguage(language: String) {
        context.languageProtoDatStore.updateData {
            it.toBuilder().setLanguage(language).build()
        }
    }
}