package com.moviearchive.data.di

import com.moviearchive.core.platform.AppContext
import com.moviearchive.data.repository.MovieRepository
import com.moviearchive.data.repository.MovieRepositoryImpl
import com.moviearchive.data.source.api.api.ApiService
import com.moviearchive.data.source.api.api.ApiServiceImpl
import com.moviearchive.data.source.api.util.HEADER.HOST
import com.moviearchive.data.source.api.util.HEADER.HOST_URL
import com.moviearchive.data.source.api.util.HEADER.KEY
import com.moviearchive.data.source.api.util.HEADER.KEY_TOKEN
import com.moviearchive.data.source.api.util.KtorLogger
import com.moviearchive.data.source.api.util.Rout
import com.moviearchive.data.source.datastore.DataStoreSource
import com.moviearchive.data.source.db.dao.MovieDao
import com.moviearchive.data.source.db.dao.MovieDaoImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(appContext: AppContext): Module

val dataModule = module {
    single<MovieRepository> { MovieRepositoryImpl(api = get(), dao = get(), dataStore = get()) }

    single { DataStoreSource() }

    single {
        HttpClient() {
            install(Logging) {
                logger = KtorLogger()
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url(Rout.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(KEY, KEY_TOKEN)
                header(HOST, HOST_URL)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    single<MovieDao> { MovieDaoImpl(db = get()) }

    single { Dispatchers.IO }
    single<ApiService> { ApiServiceImpl(httpClient = get()) }
}