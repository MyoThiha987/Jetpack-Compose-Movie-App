package com.myothiha.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * @Author myothiha
 * Created 20/03/2024 at 4:40 PM.
 **/
interface AppLanguageRepository {
    val retrieveSelectedLanguage: Flow<String>
    suspend fun saveSelectedLanguage(language: String)
}