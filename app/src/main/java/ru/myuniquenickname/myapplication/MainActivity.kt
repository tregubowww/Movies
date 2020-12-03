package ru.myuniquenickname.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.myuniquenickname.myapplication.fragments.FragmentMoviesDetails
import ru.myuniquenickname.myapplication.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity(), FragmentMoviesList.TransactionsFragmentClicks {
    private val fragmentMoviesList = FragmentMoviesList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_fragment, fragmentMoviesList)
                .commit()
        }
    }

    override fun replaceFragment(id: Int) {
        val fragmentMoviesDetails = FragmentMoviesDetails()
        val bundle = Bundle()
        bundle.putInt(DataSource.ID, id)
        fragmentMoviesDetails.arguments = bundle
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment, fragmentMoviesDetails)
            .commit()

    }


}