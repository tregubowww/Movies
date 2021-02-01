package ru.myuniquenickname.myapplication.domain.entity

data class MovieDetails(
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
