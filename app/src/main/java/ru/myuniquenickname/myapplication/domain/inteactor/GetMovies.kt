package ru.myuniquenickname.myapplication.domain.inteactor

import ru.myuniquenickname.myapplication.data.repository.MoviesRepository
import ru.myuniquenickname.myapplication.domain.entity.Movie

class GetMovies(
    val moviesRepository: MoviesRepository
) {
    suspend fun getMoviePopularList() {
        moviesRepository.loadMoviePopularList()
    }
    suspend fun getMovieTopList() {
        moviesRepository.loadMovieTopList()
    }
    suspend fun getMovieUpcomingList() {
        moviesRepository.loadMovieUpcomingList()
    }

    suspend fun getMovieSearchList(movie: String): List<Movie> {
        return moviesRepository.loadMovieSearchList(movie)
    }
}
