package ru.myuniquenickname.myapplication.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myuniquenickname.myapplication.domain.entity.Actor
import ru.myuniquenickname.myapplication.domain.entity.MovieDetails
import ru.myuniquenickname.myapplication.domain.interactor.GetActorsInteractor
import ru.myuniquenickname.myapplication.domain.interactor.GetMovieDetailInteractor
import ru.myuniquenickname.myapplication.presentation.movie_list.LoadingState

class MovieViewModel(
    private val getMovieDetail: GetMovieDetailInteractor,
    private val getActorList: GetActorsInteractor,
) : ViewModel() {

    private val _mutableMovie = MutableLiveData<MovieDetails>()
    private val _mutableActor = MutableLiveData<List<Actor>>()
    private val _mutableLoadingState = MutableLiveData<LoadingState>()

    val movie: LiveData<MovieDetails> get() = _mutableMovie
    val actors: LiveData<List<Actor>> get() = _mutableActor
    val loadingState: LiveData<LoadingState> get() = _mutableLoadingState

    fun loadMovie(id: Long) {

        viewModelScope.launch {
            try {
                _mutableLoadingState.value = LoadingState.LOADING
                val movieDetails = getMovieDetail.getMovieFromDb(id)
                if (movieDetails != null)_mutableMovie.value = movieDetails
                _mutableMovie.value = getMovieDetail.getMovieFromNetwork(id)
                _mutableLoadingState.value = LoadingState.LOADED
            } catch (exception: Exception) {
                _mutableLoadingState.value = LoadingState.error(exception.message)
            }
        }
    }

    fun loadActors(id: Long) {
        viewModelScope.launch {
            try {
                _mutableLoadingState.value = LoadingState.LOADING
                val actors = getActorList.getActorListFromDb(id)
                if (actors != null) _mutableActor.value = actors
                _mutableActor.value = getActorList.getActorListFromNetwork(id)
                _mutableLoadingState.value = LoadingState.LOADED
            } catch (exception: Exception) {
                _mutableLoadingState.value = LoadingState.error(exception.message)
            }
        }
    }
}
