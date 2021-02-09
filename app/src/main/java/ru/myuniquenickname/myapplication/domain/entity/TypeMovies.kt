package ru.myuniquenickname.myapplication.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "type_movies",
)
data class TypeMovies(
    @PrimaryKey
    @ColumnInfo(name = "type_movie")
    val typeMovie: String
)
