package ru.myuniquenickname.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.myuniquenickname.myapplication.domain.entity.Actor

//@Dao
//interface ActorsDao {
//    @Query("SELECT * FROM actors")
//    fun findAll(): LiveData<List<Actor>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun add(users: List<Actor>)
//}
