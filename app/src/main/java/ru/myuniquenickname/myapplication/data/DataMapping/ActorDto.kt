package ru.myuniquenickname.myapplication.data.DataMapping

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ActorDto(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)