package ru.myuniquenickname.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.domain.entity.TypeMovies

@Dao
interface MovieDao {

    @Query("SELECT*  FROM movies")
    fun getListMovie(): Flow<List<Movie>>

    @Query("SELECT type_movie   FROM type_movies")
    fun getTypeMovie(): Flow<String>

    @Insert
    (onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: List<Movie>)

    @Query("DELETE FROM movies")
    fun deleteMovies()

    @Transaction
    fun refreshMovies(movies: List<Movie>) {
        deleteMovies()
        addMovies(movies)
    }

    @Transaction
    fun refreshTypeMovies(typeMovies: TypeMovies) {
        deleteTypeMovies()
        addTypeMovies(typeMovies)
    }

    @Query("DELETE FROM type_movies")
    fun deleteTypeMovies()

    @Insert
    (onConflict = OnConflictStrategy.REPLACE)
    fun addTypeMovies(typeMovies: TypeMovies)
}
