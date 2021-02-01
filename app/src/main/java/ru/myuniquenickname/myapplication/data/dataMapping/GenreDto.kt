package ru.myuniquenickname.myapplication.data.dataMapping

import kotlinx.serialization.Serializable

@Serializable
data class GenresListDto(
    val genres: List<GenreDto>
)

@Serializable
data class GenreDto(
    val id: Long,
    val name: String
)
