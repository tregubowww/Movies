package ru.myuniquenickname.myapplication.data.dao

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.myuniquenickname.myapplication.data.DataMapping.MovieDto
import ru.myuniquenickname.myapplication.domain.entity.Actor
import ru.myuniquenickname.myapplication.domain.entity.Genre
import ru.myuniquenickname.myapplication.domain.entity.Movie

class MoviesLoadDao(
    private val context: Context,
    private val actorLoadDao: ActorLoadDao,
    private val genresLoadDao: GenresLoadDao) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val actorsMap = actorLoadDao.loadActors()
        val genresMap = genresLoadDao.loadGenres()
        val data = readAssetFileToString(context, "data.json")
        parseMovies(data, genresMap, actorsMap)
    }

    private fun parseMovies(
        data: String,
        genres: List<Genre>,
        actors: List<Actor>
    ): List<Movie> {
        val genresMap = genres.associateBy { it.id }
        val actorsMap = actors.associateBy { it.id }

        val jsonMovies = jsonFormat.decodeFromString<List<MovieDto>>(data)

        return jsonMovies.map { jsonMovie ->
            @Suppress("unused")
            (Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                overview = jsonMovie.overview,
                poster = jsonMovie.posterPicture,
                backdrop = jsonMovie.backdropPicture,
                ratings = jsonMovie.ratings,
                numberOfRatings = jsonMovie.votesCount,
                minimumAge = if (jsonMovie.adult) 16 else 13,
                runtime = jsonMovie.runtime,
                like = false,
                genres = jsonMovie.genreIds.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = jsonMovie.actors.map {
                    actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
                }
            ))
        }
    }

    private fun readAssetFileToString(context: Context, fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }
}