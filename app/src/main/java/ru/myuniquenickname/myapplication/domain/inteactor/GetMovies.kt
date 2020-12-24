package ru.myuniquenickname.myapplication.domain.inteactor

import ru.myuniquenickname.myapplication.data.dao.MoviesLoadDao
import ru.myuniquenickname.myapplication.domain.entity.Movie

class GetMovies(
    private val moviesLoadDao: MoviesLoadDao
) {
    suspend fun getMovies(): List<Movie> {
        return moviesLoadDao.loadMovies()
    }
}