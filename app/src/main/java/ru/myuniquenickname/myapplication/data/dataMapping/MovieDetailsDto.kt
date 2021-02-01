package ru.myuniquenickname.myapplication.data.dataMapping

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    val genres: List<GenreDto>,
    val id: Long,
    val overview: String,

    val runtime: Long,
    val title: String,

    @SerialName("vote_average")
    val rating: Float,

    @SerialName("vote_count")
    val ratingCount: Long
)
