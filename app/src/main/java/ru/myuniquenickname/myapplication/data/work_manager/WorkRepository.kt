package ru.myuniquenickname.myapplication.data.work_manager

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import java.util.concurrent.TimeUnit

class WorkRepository {
    private val constraints =
        Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
    val constrainedRequest = PeriodicWorkRequest.Builder(MoviesWorker::class.java, 8, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()
}
