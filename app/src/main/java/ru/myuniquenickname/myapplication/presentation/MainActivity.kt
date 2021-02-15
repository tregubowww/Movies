package ru.myuniquenickname.myapplication.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.databinding.ActivityMainBinding
import ru.myuniquenickname.myapplication.presentation.MovieNotifications.Companion.MOVIE_TAG
import ru.myuniquenickname.myapplication.presentation.movie_details.MovieFragment
import ru.myuniquenickname.myapplication.presentation.movie_list.MovieListFragment


class MainActivity : AppCompatActivity(), TransactionsFragmentClicks {
    private val fragmentMoviesList = MovieListFragment()
    private lateinit var binding: ActivityMainBinding
    val notifications: MovieNotifications by inject<MovieNotifications>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_fragment, fragmentMoviesList)
                .commit()
            intent?.let(::handleIntent)
        }
    }

    override fun replaceFragmentWithMovieDetails(id: Long) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment, MovieFragment.newInstance(id))
            .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toLongOrNull()
                if (id != null) {
                    replaceFragmentWithMovieDetails(id)
                }
            }
        }
    }

}

