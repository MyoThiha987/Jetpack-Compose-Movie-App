package com.myothiha.data.cache.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.myothiha.cleanarchitecturestarterkit.data.AppLanguageProto
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * @Author myothiha
 * Created 19/03/2024 at 5:16 PM.
 **/

val Context.languageProtoDatStore: DataStore<AppLanguageProto> by dataStore(
    fileName = "language_state.pb",
    serializer = LanguageProtoDataStoreSerializer
)

object LanguageProtoDataStoreSerializer : Serializer<AppLanguageProto> {
    override val defaultValue: AppLanguageProto
        get() = AppLanguageProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppLanguageProto {
        return try {
            AppLanguageProto.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: AppLanguageProto, output: OutputStream) {
        t.writeTo(output)
    }

}