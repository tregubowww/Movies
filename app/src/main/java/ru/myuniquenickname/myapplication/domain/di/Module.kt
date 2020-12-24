package ru.myuniquenickname.myapplication.domain.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.myuniquenickname.myapplication.data.dao.ActorLoadDao
import ru.myuniquenickname.myapplication.data.dao.GenresLoadDao
import ru.myuniquenickname.myapplication.data.dao.MoviesLoadDao
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovies
import ru.myuniquenickname.myapplication.presentation.ViewModelMovie

val appModule = module {
    single { ActorLoadDao(androidContext()) }
    single { GenresLoadDao(androidContext()) }
    single { MoviesLoadDao(androidContext(), get(), get()) }
    single { GetMovies(get()) }
}

val viewModel = module {
    viewModel { ViewModelMovie(get()) }
}

