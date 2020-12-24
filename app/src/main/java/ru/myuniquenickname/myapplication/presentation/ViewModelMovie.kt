package ru.myuniquenickname.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovies

class ViewModelMovie(private val getMovies: GetMovies) : ViewModel() {

    private val _mutableMoviesList = MutableLiveData<List<Movie>>()
    private val _mutableLoadingState = MutableLiveData<Boolean>()

    val mutableMovie = MutableLiveData<Movie>()
    val movieList: LiveData<List<Movie>> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    private fun loadMovies() {
        viewModelScope.launch {
            _mutableLoadingState.value = true
            _mutableMoviesList.value = getMovies.getMovies()
            _mutableLoadingState.value = false
        }
    }

    init {
        loadMovies()
    }
}