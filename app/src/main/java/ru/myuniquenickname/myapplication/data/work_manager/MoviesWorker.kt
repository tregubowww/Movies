package ru.myuniquenickname.myapplication.data.work_manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.first
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.myuniquenickname.myapplication.data.repository.MoviesRepository
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.presentation.MovieNotifications

class MoviesWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params), KoinComponent {
    override suspend fun doWork(): Result {
        return try {
            val moviesRepository: MoviesRepository by inject()
            val typeMovie = moviesRepository.getLiveDataTypeMovies().first()
            val notification = MovieNotifications(context)
            notification.initializeChanel()
            val listMovieBefore = moviesRepository.getFlowMovieList().first()

            Log.i("work", "Это мое сообщение для записи в журнале");
            when (typeMovie) {
                MoviesRepository.POPULAR_MOVIES -> {
                    moviesRepository.loadMoviePopularList()
                    val listMovieAfter = moviesRepository.getFlowMovieList().first()
                    findMaxRatingMovie(listMovieBefore, listMovieAfter, notification)
                }
                MoviesRepository.TOP_MOVIES -> {
                    moviesRepository.loadMovieTopList()
                    val listMovieAfter = moviesRepository.getFlowMovieList().first()
                    findMaxRatingMovie(listMovieBefore, listMovieAfter, notification)
                }
                MoviesRepository.UPCOMING_MOVIES -> {
                    moviesRepository.loadMovieUpcomingList()
                    val listMovieAfter = moviesRepository.getFlowMovieList().first()
                    findMaxRatingMovie(listMovieBefore, listMovieAfter, notification)
                }
                else -> moviesRepository.loadMoviePopularList()
            }
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private fun findMaxRatingMovie(
        listMovieBefore: List<Movie>?,
        listMovieAfter: List<Movie>?,
        movieNotifications: MovieNotifications
    ) {
        listMovieAfter?.minus(listMovieBefore)
        var movieMaxRating = listMovieAfter?.maxByOrNull { it.ratings }
        if (movieMaxRating != null) {
            movieNotifications.showNotification(movieMaxRating)
        } else {
            movieMaxRating = listMovieBefore?.maxByOrNull { it.ratings }
            if (movieMaxRating != null) movieNotifications.showNotification(movieMaxRating)
        }
    }

}
