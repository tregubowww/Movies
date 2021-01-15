package ru.myuniquenickname.myapplication.data.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myuniquenickname.myapplication.data.api.MoviesApi
import ru.myuniquenickname.myapplication.data.dataMapping.GenreDto
import ru.myuniquenickname.myapplication.data.dataMapping.ImageDto
import ru.myuniquenickname.myapplication.data.dataMapping.ResultMoviesDto
import ru.myuniquenickname.myapplication.domain.entity.Genre
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.presentation.utils.ADULT
import ru.myuniquenickname.myapplication.presentation.utils.MINOR
import ru.myuniquenickname.myapplication.presentation.utils.POSTER_SIZES_W342

class MoviesLoadDao(
    private val movieApi: MoviesApi
) {

    suspend fun loadMoviePopularList(): List<Movie> = withContext(Dispatchers.IO) {
        parseMovies(
            movieApi.getGenres().genres,
            movieApi.getMoviesPopular().results,
            movieApi.getImage()
        )
    }
    suspend fun loadMovieTopList(): List<Movie> = withContext(Dispatchers.IO) {
        parseMovies(
            movieApi.getGenres().genres,
            movieApi.getMoviesTopMovies().results,
            movieApi.getImage()
        )
    }
    suspend fun loadMovieUpcomingList(): List<Movie> = withContext(Dispatchers.IO) {
        parseMovies(
            movieApi.getGenres().genres,
            movieApi.getMoviesUpcoming().results,
            movieApi.getImage()
        )
    }

    suspend fun loadMovieSearchList(movie: String): List<Movie> = withContext(Dispatchers.IO) {
        parseMovies(
            movieApi.getGenres().genres,
            movieApi.getMoviesSearch(movie).results,
            movieApi.getImage()
        )
    }

    private fun parseMovies(
        genresDto: List<GenreDto>,
        moviesDto: List<ResultMoviesDto>,
        imageDto: ImageDto

    ): List<Movie> {
        val imageBaseUrl = imageDto.images.secureBaseURL + imageDto.images.posterSizes[POSTER_SIZES_W342]
        val genresMap = genresDto.map { Genre(id = it.id, name = it.name) }.associateBy { it.id }

        return moviesDto.map { movieDto ->
            @Suppress("unused")
            (
                Movie
                (
                    id = movieDto.id,
                    title = movieDto.title,
                    overview = movieDto.overview,
                    poster = imageBaseUrl + movieDto.posterPath,
                    backdrop = movieDto.backdropPath ?: "",
                    ratings = movieDto.rating,
                    numberOfRatings = movieDto.ratingCount,
                    minimumAge = if (movieDto.adult) ADULT else MINOR,
                    like = false,
                    genres = movieDto.genreIDS.map {
                        genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                    },
                )
                )
        }
    }
}
