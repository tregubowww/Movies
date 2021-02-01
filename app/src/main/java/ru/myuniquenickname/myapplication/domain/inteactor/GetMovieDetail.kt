package ru.myuniquenickname.myapplication.domain.inteactor

import ru.myuniquenickname.myapplication.data.repository.MovieDetailsRepository
import ru.myuniquenickname.myapplication.domain.entity.MovieDetails

class GetMovieDetail(
    private val moviesDetailsRepository: MovieDetailsRepository
) {

    suspend fun getMovieFromDb(id: Long): MovieDetails? {
        return moviesDetailsRepository.loadMovie(id)
    }

    suspend fun getMovieFromNetwork(id: Long): MovieDetails {
        return moviesDetailsRepository.refreshMovie(id)
    }
}
