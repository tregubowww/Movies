package ru.myuniquenickname.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.myuniquenickname.myapplication.data.dao.MovieDao
import ru.myuniquenickname.myapplication.domain.entity.Movie

//import ru.myuniquenickname.myapplication.domain.entity.MovieType

@Database(entities = [Movie::class,
//    MoviePopular::class,
//    MovieTop::class,
//    MovieUpcoming::class
//    MovieType::class
], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        const val NAME_DB = "movie_database"
    }
}
