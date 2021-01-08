package ru.myuniquenickname.myapplication.domain.inteactor

import ru.myuniquenickname.myapplication.data.dao.MoviesLoadDao
import ru.myuniquenickname.myapplication.domain.entity.Movie

class GetMovies(
    private val moviesLoadDao: MoviesLoadDao
) {
    suspend fun getMoviePopularList(): List<Movie> {
        return moviesLoadDao.loadMoviePopularList()
    }
    suspend fun getMovieTopList(): List<Movie> {
        return moviesLoadDao.loadMovieTopList()
    }
    suspend fun getMovieUpcomingList(): List<Movie> {
        return moviesLoadDao.loadMovieUpcomingList()
    }

    suspend fun getMovieSearchList(movie: String): List<Movie>? {
        return moviesLoadDao.loadMovieSearchList(movie)
    }
}
