package ru.myuniquenickname.myapplication.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myuniquenickname.myapplication.data.api.MoviesApi
import ru.myuniquenickname.myapplication.data.dao.ActorDao
import ru.myuniquenickname.myapplication.data.dataMapping.ActorListDto
import ru.myuniquenickname.myapplication.data.dataMapping.ImagesInfoDto
import ru.myuniquenickname.myapplication.domain.entity.Actor

class ActorRepository(
    private val movieApi: MoviesApi,
    private val actorDao: ActorDao
) {

    suspend fun loadActors(id: Long): List<Actor>? = withContext(Dispatchers.IO) {
        actorDao.getActors(id)
    }

    suspend fun refreshActor(id: Long): List<Actor> = withContext(Dispatchers.IO) {
        val actors = parseActors(
            movieApi.getActors(id),
            movieApi.getImage().images,
            id
        )
        actorDao.add(actors)
        actors
    }

    private fun parseActors(actorListDto: ActorListDto, baseUrlDto: ImagesInfoDto, id: Long): List<Actor> {
        val imageBaseUrl = baseUrlDto.secureBaseURL + baseUrlDto.profileSizes[1]
        return actorListDto.cast.map {
            Actor(
                id = it.id,
                name = it.name,
                picturePath = imageBaseUrl + it.profilePath,
                movieDetailsId = id
            )
        }
    }
}
