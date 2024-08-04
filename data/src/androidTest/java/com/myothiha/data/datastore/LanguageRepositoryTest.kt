package com.myothiha.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.myothiha.cleanarchitecturestarterkit.data.AppLanguageProto
import com.myothiha.data.cache.datastore.LanguageProtoDataStoreSerializer
import com.myothiha.data.repository.AppLanguageRepositoryImpl
import com.myothiha.domain.repository.AppLanguageRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import java.io.File

/**
 * @Author Liam
 * Created at 02/Aug/2024
 */
const val TEST_DATA_STORE_FILE_NAME = "testStore.pb"
private val testContext: Context = ApplicationProvider.getApplicationContext()


@RunWith(AndroidJUnit4::class)
class LanguageRepositoryTest {

    private lateinit var dataStore: DataStore<AppLanguageProto>
    private lateinit var repository: AppLanguageRepository

    fun createDataStore() {
        dataStore = DataStoreFactory.create(
            serializer = LanguageProtoDataStoreSerializer,
            produceFile = {
                testContext.dataStoreFile(fileName = TEST_DATA_STORE_FILE_NAME)
            }
        )
        repository = AppLanguageRepositoryImpl(testContext)
    }

    @Before
    fun setUp() {
        createDataStore()
    }

    @Test
    fun test_save_mm_language()= runBlocking {
        val selectedLanguage = "my"
        repository.saveSelectedLanguage(selectedLanguage)
        val retrieveLanguage = repository.retrieveSelectedLanguage.first()
        assertEquals(selectedLanguage,retrieveLanguage)
    }

    @Test
    fun test_read_mm_language() = runBlocking {
        assertEquals("my",repository.retrieveSelectedLanguage.first())
    }

    @Test
    fun test_save_en_language()= runBlocking {
        val selectedLanguage = "en"
        repository.saveSelectedLanguage(selectedLanguage)
        val retrieveLanguage = repository.retrieveSelectedLanguage.first()
        assertEquals(selectedLanguage,retrieveLanguage)
    }

    @Test
    fun test_read_en_language() = runBlocking {
        assertEquals("en",repository.retrieveSelectedLanguage.first())
    }

    @After
    fun cleanup() {
        File(testContext.filesDir, "datastore").deleteRecursively()
    }

}