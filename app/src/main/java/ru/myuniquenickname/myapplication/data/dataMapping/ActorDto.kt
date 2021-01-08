package ru.myuniquenickname.myapplication.data.dataMapping

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorListDto(
    val id: Long,
    val cast: List<ActorDto>,
)

@Serializable
data class ActorDto(
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    val name: String,
    val popularity: Double,

    @SerialName("profile_path")
    val profilePath: String? = null
)
