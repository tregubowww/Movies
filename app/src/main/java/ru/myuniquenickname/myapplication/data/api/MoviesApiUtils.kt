package ru.myuniquenickname.myapplication.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.myuniquenickname.myapplication.BuildConfig

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(MoviesApiHeaderInterceptor())
        .build()
}

fun provideJson(): Json {
    return Json {
        ignoreUnknownKeys = true
    }
}

@Suppress("EXPERIMENTAL_API_USAGE")
fun provideRetrofit(factory: Json, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(factory.asConverterFactory("application/json".toMediaType()))
        .build()
}
fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
    return retrofit.create()
}
