package ru.myuniquenickname.myapplication.data.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myuniquenickname.myapplication.data.api.MoviesApi
import ru.myuniquenickname.myapplication.data.dataMapping.ImagesDto
import ru.myuniquenickname.myapplication.data.dataMapping.MovieDetailsDto
import ru.myuniquenickname.myapplication.domain.entity.MovieDetails

class MovieDetailsLoadDao(
    private val movieApi: MoviesApi
) {
    suspend fun loadMovie(id: Long): MovieDetails = withContext(Dispatchers.IO) {
        parseMovie(movieApi.getMovie(id), movieApi.getImage().images)
    }

    private fun parseMovie(
        movieDetailsDto: MovieDetailsDto,
        baseUrlDto: ImagesDto
    ): MovieDetails {
        val imageBaseUrl = baseUrlDto.secureBaseURL + baseUrlDto.backdropSizes[1]
        return MovieDetails(
            like = false,
            backdrop = imageBaseUrl + movieDetailsDto.backdropPath,
            minimumAge = if (movieDetailsDto.adult) 16 else 13,
            ratings = movieDetailsDto.rating,
            title = movieDetailsDto.title,
            overview = movieDetailsDto.overview,
            numberOfRatings = movieDetailsDto.ratingCount,
            runtime = movieDetailsDto.runtime,
            genres = movieDetailsDto.genres.joinToString { it.name }
        )
    }
}
