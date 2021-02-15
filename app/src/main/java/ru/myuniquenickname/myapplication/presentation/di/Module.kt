package ru.myuniquenickname.myapplication.presentation.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.myuniquenickname.myapplication.data.api.provideHttpClient
import ru.myuniquenickname.myapplication.data.api.provideJson
import ru.myuniquenickname.myapplication.data.api.provideMoviesApi
import ru.myuniquenickname.myapplication.data.api.provideRetrofit
import ru.myuniquenickname.myapplication.data.db.provideActorDao
import ru.myuniquenickname.myapplication.data.db.provideDatabase
import ru.myuniquenickname.myapplication.data.db.provideMovieDao
import ru.myuniquenickname.myapplication.data.db.provideMovieDetailsDao
import ru.myuniquenickname.myapplication.data.repository.ActorRepository
import ru.myuniquenickname.myapplication.data.repository.MovieDetailsRepository
import ru.myuniquenickname.myapplication.data.repository.MoviesRepository
import ru.myuniquenickname.myapplication.domain.interactor.GetActorsInteractor
import ru.myuniquenickname.myapplication.domain.interactor.GetMovieDetailInteractor
import ru.myuniquenickname.myapplication.domain.interactor.GetMoviesInteractor
import ru.myuniquenickname.myapplication.presentation.MovieNotifications
import ru.myuniquenickname.myapplication.presentation.movie_details.MovieViewModel
import ru.myuniquenickname.myapplication.presentation.movie_list.MovieListViewModel

val appModule = module {

    single { GetActorsInteractor(get()) }
    single { GetMoviesInteractor(get()) }
    single { GetMovieDetailInteractor(get()) }


    single { MovieNotifications(androidApplication()) }
}

val viewModel = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieViewModel(get(), get()) }
}

val apiModule = module {
    single { provideHttpClient() }
    single { provideJson() }
    single { provideRetrofit(get(), get()) }
    single { provideMoviesApi(get()) }
}

val databaseModule = module {

    single { provideDatabase(androidApplication()) }
    single { provideMovieDao(get()) }
    single { provideMovieDetailsDao(get()) }
    single { provideActorDao(get()) }
}
val repositoryModule = module {
    single { ActorRepository(get(), get()) }
    single { MovieDetailsRepository(get(), get()) }
    single { MoviesRepository(get(), get()) }
}
