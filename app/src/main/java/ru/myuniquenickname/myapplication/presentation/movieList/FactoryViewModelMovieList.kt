package ru.myuniquenickname.myapplication.presentation.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovies
import ru.myuniquenickname.myapplication.presentation.ViewModelMovie

class FactoryViewModelMovieList (private val getMovies: GetMovies): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        ViewModelMovie::class.java -> ViewModelMovie(getMovies)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}