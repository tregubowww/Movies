package ru.myuniquenickname.myapplication.presentation.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovies

class MovieListViewModel(private val getMovies: GetMovies) : ViewModel() {

    private val _mutableMoviesList = MutableLiveData<List<Movie>>()
    private val _mutableLoadingState = MutableLiveData<Boolean>()

    val movieList: LiveData<List<Movie>> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    fun loadPopularMovies() {
        viewModelScope.launch {
            _mutableLoadingState.value = true
            _mutableMoviesList.value = getMovies.getMoviePopularList()
            _mutableLoadingState.value = false
        }
    }
    fun loadTopMovies() {
        viewModelScope.launch {
            _mutableLoadingState.value = true
            _mutableMoviesList.value = getMovies.getMovieTopList()
            _mutableLoadingState.value = false
        }
    }
    fun loadUpcomingMovies() {
        viewModelScope.launch {
            _mutableLoadingState.value = true
            _mutableMoviesList.value = getMovies.getMovieUpcomingList()
            _mutableLoadingState.value = false
        }
    }

    fun searchMovie(movie: String) {
        viewModelScope.launch {
            _mutableLoadingState.value = true
            _mutableMoviesList.value = getMovies.getMovieSearchList(movie)
            _mutableLoadingState.value = false
        }
    }

    init {
        loadPopularMovies()
    }
}
