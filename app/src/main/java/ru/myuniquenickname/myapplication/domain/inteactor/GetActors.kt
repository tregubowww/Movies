package ru.myuniquenickname.myapplication.domain.inteactor

import ru.myuniquenickname.myapplication.data.repository.ActorRepository
import ru.myuniquenickname.myapplication.domain.entity.Actor

class GetActors(
    private val actorRepository: ActorRepository
) {
    suspend fun getActorListFromDb(id: Long): List<Actor>? {
        return actorRepository.loadActors(id)
    }
    suspend fun getActorListFromNetwork(id: Long): List<Actor> {
        return actorRepository.refreshActor(id)
    }
}
