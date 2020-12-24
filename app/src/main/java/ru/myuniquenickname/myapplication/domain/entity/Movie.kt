package ru.myuniquenickname.myapplication.domain.entity

import ru.myuniquenickname.myapplication.domain.entity.Actor
import ru.myuniquenickname.myapplication.domain.entity.Genre

data class Movie(
    var like: Boolean,
    val id: Int,
    val poster: String,
    val backdrop: String,
    val minimumAge: Int,
    val ratings: Float,
    val title: String,
    val overview: String,
    val numberOfRatings: Int,
    val runtime: Int,
    val actors: List<Actor>,
    val genres: List<Genre>

)


