package com.myothiha.data.cache.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.myothiha.cleanarchitecturestarterkit.data.UserProto
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * @Author myothiha
 * Created 19/03/2024 at 5:16 PM.
 **/

val Context.userProtoDatStore: DataStore<UserProto> by dataStore(
    fileName = "user_state.pb",
    serializer = ThemeProtoDataStoreSerializer
)

object ThemeProtoDataStoreSerializer : Serializer<UserProto> {
    override val defaultValue: UserProto
        get() = UserProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserProto {
        return try {
            UserProto.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: UserProto, output: OutputStream) {
        t.writeTo(output)
    }

}