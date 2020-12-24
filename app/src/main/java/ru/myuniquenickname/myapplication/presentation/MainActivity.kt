package ru.myuniquenickname.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.presentation.movieDetails.FragmentMoviesDetails
import ru.myuniquenickname.myapplication.presentation.movieList.FragmentMovieList

class MainActivity : AppCompatActivity(), FragmentMovieList.TransactionsFragmentClicks {
    private val fragmentMoviesList = FragmentMovieList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_fragment, fragmentMoviesList)
                .commit()
        }
    }

    override fun replaceFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment, FragmentMoviesDetails())
            .commit()
    }
}