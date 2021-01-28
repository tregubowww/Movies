package ru.myuniquenickname.myapplication.data.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myuniquenickname.myapplication.data.api.MoviesApi
import ru.myuniquenickname.myapplication.data.dataMapping.ImagesInfoDto
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
    companion object{
        const val ADULT = 16
        const val MINOR = 13
        const val BACKDROP_SIZES_W780 = 1
    }
}
