package ru.myuniquenickname.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.myuniquenickname.myapplication.domain.entity.Actor

@Dao
interface ActorDao {
    @Query("SELECT*  FROM actors WHERE movie_details_id = :idMovie")
    fun getActors(idMovie: Long): List<Actor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(actors: List<Actor>)
}
