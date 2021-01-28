package ru.myuniquenickname.myapplication.data.dataMapping

import androidx.room.Entity
import androidx.room.PrimaryKey
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
