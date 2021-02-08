package ru.myuniquenickname.myapplication.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.myuniquenickname.myapplication.data.api.MoviesApi
import ru.myuniquenickname.myapplication.data.dao.MovieDao
import ru.myuniquenickname.myapplication.data.dataMapping.GenreDto
import ru.myuniquenickname.myapplication.data.dataMapping.ImageDto
import ru.myuniquenickname.myapplication.data.dataMapping.ResultMoviesDto
import ru.myuniquenickname.myapplication.domain.entity.Genre
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.domain.entity.TypeMovies

class MoviesRepository(private val movieApi: MoviesApi, private val movieDao: MovieDao) {

    fun getFlowMovieList(): Flow<List<Movie>> {
        return movieDao.getListMovie()
    }
    fun getFlowTypeMovies(): Flow<String> {
        return movieDao.getTypeMovie()
    }

    suspend fun loadMoviePopularList() = withContext(Dispatchers.IO) {
        val listMovies = parseMovies(
            movieApi.getGenres().genres,
            movieApi.getMoviesPopular().results,
            movieApi.getImage(),
        )
        movieDao.refreshTypeMovies(TypeMovies(POPULAR_MOVIES))
        movieDao.refreshMovies(listMovies)
    }

    suspend fun loadMovieTopList() = withContext(Dispatchers.IO) {
        val listMovies = parseMovies(
            movieApi.getGenres().genres,
            movieApi.getMoviesTopMovies().results,
            movieApi.getImage(),
        )
        movieDao.deleteTypeMovies()
        movieDao.addTypeMovies(TypeMovies(TOP_MOVIES))
        movieDao.deleteMovies()
        movieDao.addMovies(listMovies)
    }

    suspend fun loadMovieUpcomingList() = withContext(Dispatchers.IO) {
        val listMovies = parseMovies(
            movieApi.getGenres().genres,
            movieApi.getMoviesUpcoming().results,
            movieApi.getImage(),
        )
        movieDao.deleteTypeMovies()
        movieDao.addTypeMovies(TypeMovies(UPCOMING_MOVIES))
        movieDao.deleteMovies()
        movieDao.addMovies(listMovies)
    }

    suspend fun loadMovieSearchList(movie: String): List<Movie> = withContext(Dispatchers.IO) {
        parseMovies(
            movieApi.getGenres().genres,
            movieApi.getMoviesSearch(movie).results,
            movieApi.getImage(),
        )
    }

    private fun parseMovies(
        genresDto: List<GenreDto>,
        moviesDto: List<ResultMoviesDto>,
        imageDto: ImageDto,

    ): List<Movie> {
        val imageBaseUrl =
            imageDto.images.secureBaseURL + imageDto.images.posterSizes[POSTER_SIZES_W342]
        val genresMap = genresDto.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }.associateBy { it.id }

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
                    }.joinToString { it.name },
                )
                )
        }
    }

    companion object {
        const val ADULT = 16
        const val MINOR = 13
        const val POSTER_SIZES_W342 = 3
        const val POPULAR_MOVIES: String = "Popular"
        const val TOP_MOVIES: String = "Top"
        const val UPCOMING_MOVIES: String = "Upcoming"
    }
}
