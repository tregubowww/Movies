package ru.myuniquenickname.myapplication.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.myuniquenickname.myapplication.data.repository.MoviesRepository
import ru.myuniquenickname.myapplication.domain.entity.Movie

class GetMoviesInteractor(
    val moviesRepository: MoviesRepository
) {
    suspend fun loadMoviePopularList() {
        moviesRepository.loadMoviePopularList()
    }
    suspend fun loadMovieTopList() {
        moviesRepository.loadMovieTopList()
    }
    suspend fun loadMovieUpcomingList() {
        moviesRepository.loadMovieUpcomingList()
    }

    suspend fun loadMovieSearchList(movie: String): List<Movie> {
        return moviesRepository.loadMovieSearchList(movie)
    }
    fun getTypeMovies(): Flow<String> {
        return moviesRepository.getLiveDataTypeMovies()
    }
    fun getMovieList(): Flow<List<Movie>> {
        return moviesRepository.getFlowMovieList()
    }
}
