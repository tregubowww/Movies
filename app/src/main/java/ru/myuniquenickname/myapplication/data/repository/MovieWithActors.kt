package ru.myuniquenickname.myapplication.data.repository

import androidx.room.Relation
import ru.myuniquenickname.myapplication.domain.entity.Actor

data class MovieWithActors (
    val id: Long,
    var like: Boolean,
    val poster: String,
    val backdrop: String,
    val minimumAge: Int,
    val ratings: Float,
    val title: String,
    val overview: String,
    val numberOfRatings: Long,
    @Relation(parentColumn = "id", entityColumn = "movie_id")
    val listActors: List<Actor>
)
