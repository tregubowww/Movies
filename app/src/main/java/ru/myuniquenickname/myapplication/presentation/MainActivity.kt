package ru.myuniquenickname.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.databinding.ActivityMainBinding
import ru.myuniquenickname.myapplication.presentation.movie_details.MovieFragment
import ru.myuniquenickname.myapplication.presentation.movie_list.MovieListFragment

class MainActivity : AppCompatActivity(), TransactionsFragmentClicks {
    private val fragmentMoviesList = MovieListFragment()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_fragment, fragmentMoviesList)
                .commit()
        }
    }

    override fun replaceFragmentWithMovieDetails(id: Long) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment, MovieFragment.newInstance(id))
            .commit()
    }
}
