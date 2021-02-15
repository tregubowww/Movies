package ru.myuniquenickname.myapplication.presentation

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.domain.entity.Movie

class MovieNotifications(private val context: Context) {

    companion object {
        private const val CHANNEL_NEW_MOVIE = "new_movie"

        private const val REQUEST_CONTENT = 1

        private const val MOVIE_TAG = "movie"
    }

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    fun initializeChanel() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIE) == null) {
        }
        notificationManagerCompat.createNotificationChannel(
            NotificationChannelCompat.Builder(
                CHANNEL_NEW_MOVIE,
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
                .setName(context.getString(R.string.channel_new_movie))
                .build()
        )
    }


    fun showNotification(movie: Movie) {
        val contentUri = "https://android.example.com/movie/${movie.id}".toUri()


        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIE)
            .setContentTitle(movie.title)
            .setContentText(movie.overview)
            .setSmallIcon(R.drawable.ic_baseline_movie_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        Glide
            .with(context)
            .asBitmap()
            .load(movie.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    builder.setLargeIcon(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
        notificationManagerCompat.notify(MOVIE_TAG, movie.id.toInt(), builder.build())
    }
}
