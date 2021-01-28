package ru.myuniquenickname.myapplication.data.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myuniquenickname.myapplication.data.api.MoviesApi
import ru.myuniquenickname.myapplication.data.dataMapping.ActorListDto
import ru.myuniquenickname.myapplication.data.dataMapping.ImagesInfoDto
import ru.myuniquenickname.myapplication.domain.entity.Actor

class ActorsLoadDao(private val movieApi: MoviesApi) {

    suspend fun loadActors(id: Long): List<Actor> = withContext(Dispatchers.IO) {
        parseActors(
            movieApi.getActors(id),
            movieApi.getImage().images
        )
    }

    private fun parseActors(actorListDto: ActorListDto, baseUrlDto: ImagesInfoDto): List<Actor> {
        val imageBaseUrl = baseUrlDto.secureBaseURL + baseUrlDto.profileSizes[1]
        return actorListDto.cast.map {
            Actor(
                id = it.id,
                name = it.name,
                picturePath = imageBaseUrl + it.profilePath
            )
        }
    }
}
