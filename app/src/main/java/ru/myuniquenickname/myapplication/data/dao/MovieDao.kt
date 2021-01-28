package ru.myuniquenickname.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.myuniquenickname.myapplication.domain.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT*  FROM movies")
    fun getListPopularMovie(): Flow<List<Movie>>

    @Insert
        (onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: List<Movie>)

    @Query("DELETE FROM movies")
    fun delete()
}
