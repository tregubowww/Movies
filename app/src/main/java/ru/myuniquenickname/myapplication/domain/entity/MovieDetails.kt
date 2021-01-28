package ru.myuniquenickname.myapplication.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_details")
data class MovieDetails(
    @PrimaryKey val id: Long,
    var like: Boolean,
    val backdrop: String,
    val minimumAge: Int,
    val ratings: Float,
    val title: String,
    val overview: String,
    val numberOfRatings: Long,
    val runtime: Long,
    val genres: String
)
