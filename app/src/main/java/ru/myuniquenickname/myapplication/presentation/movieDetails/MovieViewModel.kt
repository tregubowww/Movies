package ru.myuniquenickname.myapplication.presentation.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myuniquenickname.myapplication.domain.entity.Actor
import ru.myuniquenickname.myapplication.domain.entity.MovieDetails
import ru.myuniquenickname.myapplication.domain.inteactor.GetActors
import ru.myuniquenickname.myapplication.domain.inteactor.GetMovieDetail

class MovieViewModel(
    private val getMovieDetail: GetMovieDetail,
    private val getActorList: GetActors,
) : ViewModel() {

    private val _mutableMovie = MutableLiveData<MovieDetails>()
    private val _mutableActor = MutableLiveData<List<Actor>>()
    private val _mutableLoadingState = MutableLiveData<Boolean>()

    val movie: LiveData<MovieDetails> get() = _mutableMovie
    val actors: LiveData<List<Actor>> get() = _mutableActor
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    fun loadMovie(id: Long) {
        viewModelScope.launch {
            _mutableLoadingState.value = true
            _mutableMovie.value = getMovieDetail.getMovie(id)
            _mutableLoadingState.value = false
        }
    }

    fun loadActors(id: Long) {
        viewModelScope.launch {
            _mutableLoadingState.value = true
            _mutableActor.value = getActorList.getActors(id)
            _mutableLoadingState.value = false
        }
    }
}
