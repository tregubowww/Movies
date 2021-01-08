package ru.myuniquenickname.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.presentation.movieDetails.MovieFragment
import ru.myuniquenickname.myapplication.presentation.movieList.MovieListFragment

class MainActivity : AppCompatActivity(), MovieListFragment.TransactionsFragmentClicks {
    private val fragmentMoviesList = MovieListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_fragment, fragmentMoviesList)
                .commit()
        }
    }

    override fun replaceFragment(id: Long) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment, MovieFragment.newInstance(id))
            .commit()
    }
}
