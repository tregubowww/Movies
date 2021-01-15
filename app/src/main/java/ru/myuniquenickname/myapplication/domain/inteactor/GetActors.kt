package ru.myuniquenickname.myapplication.domain.inteactor

import ru.myuniquenickname.myapplication.data.dao.ActorsLoadDao
import ru.myuniquenickname.myapplication.domain.entity.Actor

class GetActors(
    private val actorsLoadDao: ActorsLoadDao
) {
    suspend fun getActorList(id: Long): List<Actor> {
        return actorsLoadDao.loadActors(id)
    }
}
