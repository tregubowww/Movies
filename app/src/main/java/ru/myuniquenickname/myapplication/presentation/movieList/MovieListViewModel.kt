package ru.myuniquenickname.myapplication.presentation.movieList

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovies
import java.util.concurrent.CancellationException

class MovieListViewModel(private val getMovies: GetMovies) : ViewModel() {

    private val _mutableLoadingState = MutableLiveData<LoadingState>()
    private val _mutableMovieList = MutableLiveData<List<Movie>>()

    val moviePopularList: LiveData<List<Movie>> get() = getMovies.moviesRepository.moviePopularList.asLiveData()
    val mutableMovieList get() = _mutableMovieList
    val loadingState: LiveData<LoadingState> get() = _mutableLoadingState

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val moviesListSearch: LiveData<MoviesSearchResult> get() = searchMovie(queryChannel)

    fun refreshPopularMovies() {
        viewModelScope.launch {
            try {
                _mutableLoadingState.value = LoadingState.LOADING
                getMovies.getMoviePopularList()
                _mutableLoadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _mutableLoadingState.value = LoadingState.error(e.message)
            }

        }
    }

    fun loadTopMovies() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    _mutableLoadingState.value = LoadingState.LOADING
                    _mutableMovieList.value = getMovies.getMovieTopList()
                    _mutableLoadingState.value = LoadingState.LOADED
                } catch (e: Exception) {
                    _mutableLoadingState.value = LoadingState.error(e.message)
                }

            }
        }
    }

    fun loadUpcomingMovies() {
        viewModelScope.launch {
            try {
                _mutableLoadingState.value = LoadingState.LOADING
                _mutableMovieList.value = getMovies.getMovieUpcomingList()
                _mutableLoadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _mutableLoadingState.value = LoadingState.error(e.message)
            }

        }
    }

    private fun searchMovie(queryChannel: BroadcastChannel<String>):
        LiveData<MoviesSearchResult> {
        return queryChannel.asFlow()
            .debounce(TIMEOUT_DEBOUNCE)
            .onEach {
                _mutableLoadingState.value = LoadingState.LOADING
            }
            .mapLatest {
                if (it.isEmpty()) {
                    EmptyQuery
                } else {
                    try {
                        val result = getMovies.getMovieSearchList(it)
                        if (result.isEmpty()) {
                            EmptyResult
                        } else {
                            ValidResult(result)
                        }
                    } catch (throwable: Throwable) {
                        if (throwable is CancellationException) {
                            throw throwable
                        } else {
                            Log.w(MovieListViewModel::class.java.name, throwable)
                            ErrorResult(throwable)
                        }
                    }
                }
            }
            .onEach {
                _mutableLoadingState.value = LoadingState.LOADED
            }
            .catch { emit(TerminalError) }
            .asLiveData(viewModelScope.coroutineContext)
    }
    companion object{
        const val TIMEOUT_DEBOUNCE: Long = 500
    }
}
