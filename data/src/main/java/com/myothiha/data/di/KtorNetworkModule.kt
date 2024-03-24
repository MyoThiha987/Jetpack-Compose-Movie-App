package com.myothiha.data.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.myothiha.data.network.dto.MovieDto
import com.myothiha.data.network.response.DataResponse
import com.myothiha.data.network.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:20 PM.

 **/

@Module
@InstallIn(SingletonComponent::class)
object KtorNetworkModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context
    ): HttpClient {

        val converter = KotlinxSerializationConverter(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            explicitNulls = false

        })

        // Create the Interceptor
        val chuckerInterceptor =
            ChuckerInterceptor.Builder(context).collector(ChuckerCollector(context))
                .maxContentLength(250000L).build()

        val okhttpEngine = OkHttp.create {
            addInterceptor(chuckerInterceptor)
        }
        return HttpClient(okhttpEngine) {
            expectSuccess = true

            install(ContentNegotiation) {
                register(
                    ContentType.Application.Json, converter
                )
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }


            install(ResponseObserver) {
                onResponse { response ->
//Log.d("HTTP status:", "${response.status.value}")
                    Log.d("HTTP status:", "${response.body<DataResponse<MovieDto>>()}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MWEzMWQzNWUwYTMyNzg1ZjJlNGM0NDk5ZjA0M2FlOCIsInN1YiI6IjVkYzM5NDBjOWQ4OTM5MDAxODM0YjVlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Eve0FD4yriTnRWsCD0P2bTXplUlUObIIfs1Q5ChAdgc"
                )
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }

    }

    fun HttpRequestBuilder.pathUrl(path: String,page : Int = 1)
    { url {
            takeFrom(Constants.BASE_URL)
            path("3", path)
            parameter("language", "en")
            parameter("page", page)
        }
    }
}