package ru.myuniquenickname.myapplication.data.db

import android.app.Application
import androidx.room.Room
import ru.myuniquenickname.myapplication.data.MovieDatabase
import ru.myuniquenickname.myapplication.data.dao.ActorDao
import ru.myuniquenickname.myapplication.data.dao.MovieDao
import ru.myuniquenickname.myapplication.data.dao.MovieDetailsDao

fun provideDatabase(application: Application): MovieDatabase {
    return Room.databaseBuilder(application, MovieDatabase::class.java, MovieDatabase.NAME_DB)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}
fun provideMovieDao(database: MovieDatabase): MovieDao {
    return database.movieDao
}
fun provideMovieDetailsDao(database: MovieDatabase): MovieDetailsDao {
    return database.movieDetailsDao
}
fun provideActorDao(database: MovieDatabase): ActorDao {
    return database.actorDao
}
