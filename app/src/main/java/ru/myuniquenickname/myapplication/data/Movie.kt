package ru.myuniquenickname.myapplication.data

import ru.myuniquenickname.myapplication.data.Actor
import ru.myuniquenickname.myapplication.data.Genre

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


