package ru.myuniquenickname.myapplication.data.dao

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.myuniquenickname.myapplication.data.dataMapping.GenreDto
import ru.myuniquenickname.myapplication.domain.entity.Genre
import ru.myuniquenickname.myapplication.utils.readAssetFileToString

class GenresLoadDao(private val context: Context) {
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
        val data = readAssetFileToString(context, "genres.json")
        parseGenres(data)
    }

    private fun parseGenres(data: String): List<Genre> {
        val jsonGenres = jsonFormat.decodeFromString<List<GenreDto>>(data)
        return jsonGenres.map { Genre(id = it.id, name = it.name) }
    }
}
