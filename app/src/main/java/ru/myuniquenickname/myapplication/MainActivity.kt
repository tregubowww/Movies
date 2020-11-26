package ru.myuniquenickname.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.myuniquenickname.myapplication.fragments.FragmentMoviesDetails
import ru.myuniquenickname.myapplication.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity() , FragmentMoviesList.TransactionsFragmentClicks{





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         FragmentMoviesList().apply {
             setClickListener(this@MainActivity)
             supportFragmentManager.beginTransaction().
             add(R.id.fragment, this).
             commit()
         }








    }

    override fun replaceFragment() {
        supportFragmentManager.beginTransaction().addToBackStack(null).
        replace(R.id.fragment, FragmentMoviesDetails()).commit()
    }


}