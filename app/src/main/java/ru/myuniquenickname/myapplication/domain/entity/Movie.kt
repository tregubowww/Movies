package ru.myuniquenickname.myapplication.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies",
)
data class Movie(
    @PrimaryKey val id: Long,
    var like: Boolean,
    val poster: String,
    val backdrop: String,

    @ColumnInfo(name = "minimum_age")
    val minimumAge: Int,

    val ratings: Float,
    val title: String,
    val overview: String,

    @ColumnInfo(name = "number_of_ratings")
    val numberOfRatings: Long,

    val genres: String,
)
