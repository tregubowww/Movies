package ru.myuniquenickname.myapplication.data.work_manager

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.myuniquenickname.myapplication.data.repository.MoviesRepository

class MoviesWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params), KoinComponent {
    override suspend fun doWork(): Result {
        return try {
            val moviesRepository: MoviesRepository by inject()
            val typeMovie = moviesRepository.getFlowTypeMovies().asLiveData().value
            when (typeMovie) {
                MoviesRepository.POPULAR_MOVIES -> moviesRepository.loadMoviePopularList()
                MoviesRepository.TOP_MOVIES -> moviesRepository.loadMovieTopList()
                MoviesRepository.UPCOMING_MOVIES -> moviesRepository.loadMovieUpcomingList()
                else -> moviesRepository.loadMoviePopularList()
            }
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
}
