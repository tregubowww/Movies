package ru.myuniquenickname.myapplication.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myuniquenickname.myapplication.data.api.MoviesApi
import ru.myuniquenickname.myapplication.data.dao.MovieDetailsDao
import ru.myuniquenickname.myapplication.data.dataMapping.ImagesInfoDto
import ru.myuniquenickname.myapplication.data.dataMapping.MovieDetailsDto
import ru.myuniquenickname.myapplication.domain.entity.MovieDetails

class MovieDetailsRepository(
    private val movieApi: MoviesApi,
    private val movieDetailsDao: MovieDetailsDao
) {
    suspend fun loadMovie(id: Long): MovieDetails? = withContext(Dispatchers.IO) {
        movieDetailsDao.getMovieDetails(id)
    }

    suspend fun refreshMovie(id: Long): MovieDetails = withContext(Dispatchers.IO) {
        val movie = parseMovie(movieApi.getMovie(id), movieApi.getImage().images)
        movieDetailsDao.addMovieDetails(movie)
        movie
    }

    private fun parseMovie(
        movieDetailsDto: MovieDetailsDto,
        baseUrlDto: ImagesInfoDto
    ): MovieDetails {
        val imageBaseUrl = baseUrlDto.secureBaseURL + baseUrlDto.backdropSizes[BACKDROP_SIZES_W780]
        return MovieDetails(
            id = movieDetailsDto.id,
            like = false,
            backdrop = imageBaseUrl + movieDetailsDto.backdropPath,
            minimumAge = if (movieDetailsDto.adult) ADULT else MINOR,
            ratings = movieDetailsDto.rating,
            title = movieDetailsDto.title,
            overview = movieDetailsDto.overview,
            numberOfRatings = movieDetailsDto.ratingCount,
            runtime = movieDetailsDto.runtime,
            genres = movieDetailsDto.genres.joinToString { it.name }
        )
    }

    companion object {
        const val ADULT = 16
        const val MINOR = 13
        const val BACKDROP_SIZES_W780 = 1
    }
}
