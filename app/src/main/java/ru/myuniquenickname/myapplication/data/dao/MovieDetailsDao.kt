package ru.myuniquenickname.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.myuniquenickname.myapplication.domain.entity.MovieDetails

@Dao
interface MovieDetailsDao {

    @Query("SELECT*  FROM movies_details WHERE id = :idMovie")
    fun getMovieDetails(idMovie: Long): MovieDetails

    @Insert
    (onConflict = OnConflictStrategy.REPLACE)
    fun addMovieDetails(movieDetails: MovieDetails)
}
