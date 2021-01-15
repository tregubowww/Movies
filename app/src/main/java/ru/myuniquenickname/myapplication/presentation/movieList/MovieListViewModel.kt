package ru.myuniquenickname.myapplication.presentation.movieList

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
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovies
import ru.myuniquenickname.myapplication.presentation.utils.TIMEOUT_DEBOUNCE
import java.util.concurrent.CancellationException

class MovieListViewModel(private val getMovies: GetMovies) : ViewModel() {

    private val _mutableMoviesList = MutableLiveData<List<Movie>>()
    private val _mutableLoadingState = MutableLiveData<Boolean>()

    val movieList: LiveData<List<Movie>?> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    private val _mutableMoviesListSearch = searchMovie(queryChannel)

    val moviesListSearch: LiveData<MoviesSearchResult> get() = _mutableMoviesListSearch

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

    private fun searchMovie(queryChannel: BroadcastChannel<String>):
        LiveData<MoviesSearchResult> {
            return queryChannel.asFlow()
                .debounce(TIMEOUT_DEBOUNCE)
                .onEach {
                    _mutableLoadingState.value = true
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
                        } catch (throwable : Throwable) {
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
                    _mutableLoadingState.value = false
                }
                .catch { emit(TerminalError) }
                .asLiveData(viewModelScope.coroutineContext)
        }

    init {
        loadPopularMovies()
    }
}
