package ru.myuniquenickname.myapplication.presentation.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import ru.myuniquenickname.myapplication.BuildConfig
import ru.myuniquenickname.myapplication.data.api.MoviesApi
import ru.myuniquenickname.myapplication.data.api.MoviesApiHeaderInterceptor
import ru.myuniquenickname.myapplication.data.dao.ActorsLoadDao
import ru.myuniquenickname.myapplication.data.dao.MovieDetailsLoadDao
import ru.myuniquenickname.myapplication.data.dao.MoviesLoadDao
import ru.myuniquenickname.myapplication.domain.inteactor.GetActors
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovieDetail
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovies
import ru.myuniquenickname.myapplication.presentation.movieDetails.MovieViewModel
import ru.myuniquenickname.myapplication.presentation.movieList.MovieListViewModel

val appModule = module {
    single { ActorsLoadDao(get()) }
    single { GetActors(get()) }
    single { MoviesLoadDao(get()) }
    single { GetMovies(get()) }
    single { GetMovieDetail(get()) }
    single { MovieDetailsLoadDao(get()) }
}

val viewModel = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieViewModel(get(), get()) }
}

val apiModule = module {
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create()
    }
    single { provideMoviesApi(get()) }
}

val netModule = module {

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

    single { provideHttpClient() }
    single { provideJson() }
    single { provideRetrofit(get(), get()) }
}
