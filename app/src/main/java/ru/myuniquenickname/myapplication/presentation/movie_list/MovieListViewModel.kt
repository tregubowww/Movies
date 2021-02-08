package ru.myuniquenickname.myapplication.presentation.movie_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.domain.interactor.GetMoviesInteractor
import java.util.concurrent.CancellationException

class MovieListViewModel(private val getMovies: GetMoviesInteractor) : ViewModel() {

    private val _mutableLoadingState = MutableLiveData<LoadingState>()

    val mutableTypeMovies: LiveData<String> get() = getMovies.getTypeMovies().asLiveData()
    val movieList: LiveData<List<Movie>> get() = getMovies.getMovieList().asLiveData()
    val loadingState: LiveData<LoadingState> get() = _mutableLoadingState

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val moviesListSearch: LiveData<MoviesSearchResult> get() = searchMovie(queryChannel)

    fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                _mutableLoadingState.value = LoadingState.LOADING
                getMovies.loadMoviePopularList()
                _mutableLoadingState.value = LoadingState.LOADED
            } catch (exception: Exception) {
                _mutableLoadingState.value = LoadingState.error(exception.message)
            }
        }
    }

    fun loadTopMovies() {
        viewModelScope.launch {
            try {
                _mutableLoadingState.value = LoadingState.LOADING
                getMovies.loadMovieTopList()
                _mutableLoadingState.value = LoadingState.LOADED
            } catch (exception: Exception) {
                _mutableLoadingState.value = LoadingState.error(exception.message)
            }
        }
    }

    fun loadUpcomingMovies() {
        viewModelScope.launch {
            try {
                _mutableLoadingState.value = LoadingState.LOADING
                getMovies.loadMovieUpcomingList()
                _mutableLoadingState.value = LoadingState.LOADED
            } catch (exception: Exception) {
                _mutableLoadingState.value = LoadingState.error(exception.message)
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
                            val result = getMovies.loadMovieSearchList(it)
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
    companion object {
        const val TIMEOUT_DEBOUNCE: Long = 500
    }
}
