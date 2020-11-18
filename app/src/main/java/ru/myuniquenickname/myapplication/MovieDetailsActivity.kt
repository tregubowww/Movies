package ru.myuniquenickname.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val recyclerView = findViewById<RecyclerView>(R.id.castRecyclerView)
        recyclerView.setHasFixedSize(true)

        val adapter = RecyclerViewAdapter(createList())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
    fun createList() : List<RecyclerViewICast>{
        val listCast = mutableListOf<RecyclerViewICast>()
        listCast.add(RecyclerViewICast(R.drawable.movie1, "Rober Downey Jr."))
        listCast.add(RecyclerViewICast(R.drawable.movie2, "Chris Evans"))
        listCast.add(RecyclerViewICast(R.drawable.movie3, "Mark Ruffalo"))
        listCast.add(RecyclerViewICast(R.drawable.movie4, "Chris Hemsworth"))


        return listCast
    }
}