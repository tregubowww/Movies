package ru.myuniquenickname.myapplication.data.dao

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.myuniquenickname.myapplication.data.dataMapping.ActorDto
import ru.myuniquenickname.myapplication.domain.entity.Actor
import ru.myuniquenickname.myapplication.utils.readAssetFileToString

class ActorLoadDao(private val context: Context) {
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    suspend fun loadActors(): List<Actor> = withContext(Dispatchers.IO) {
        val data = readAssetFileToString(context, "people.json")
        parseActors(data)
    }

    private fun parseActors(data: String): List<Actor> {
        val jsonActors = jsonFormat.decodeFromString<List<ActorDto>>(data)
        return jsonActors.map { Actor(id = it.id, name = it.name, picture = it.profilePicture) }
    }

}
