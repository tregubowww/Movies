package ru.myuniquenickname.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.myuniquenickname.myapplication.data.dao.ActorDao
import ru.myuniquenickname.myapplication.data.dao.MovieDao
import ru.myuniquenickname.myapplication.data.dao.MovieDetailsDao
import ru.myuniquenickname.myapplication.domain.entity.Actor
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.domain.entity.MovieDetails
import ru.myuniquenickname.myapplication.domain.entity.TypeMovies

@Database(
    entities = [
        Movie::class,
        TypeMovies::class,
        MovieDetails::class,
        Actor::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val movieDetailsDao: MovieDetailsDao
    abstract val actorDao: ActorDao

    companion object {
        const val NAME_DB = "movie_database"
    }
}
