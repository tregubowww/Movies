package ru.myuniquenickname.myapplication.domain.inteactor

import ru.myuniquenickname.myapplication.data.dao.MovieDetailsLoadDao
import ru.myuniquenickname.myapplication.domain.entity.MovieDetails

class GetMovieDetail(
    private val movieDetailsLoadDao: MovieDetailsLoadDao
) {

    suspend fun getMovie(id: Long): MovieDetails {
        return movieDetailsLoadDao.loadMovie(id)
    }
}
