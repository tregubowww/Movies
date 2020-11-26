package ru.myuniquenickname.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.RecyclerViewAdapter
import ru.myuniquenickname.myapplication.RecyclerViewICast


class FragmentMoviesDetails : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.castRecyclerView)
        recyclerView.setHasFixedSize(true)
        val adapter = RecyclerViewAdapter(createList())
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

}

fun createList(): List<RecyclerViewICast> {
    val listCast = mutableListOf<RecyclerViewICast>()
    listCast.add(RecyclerViewICast(R.drawable.movie1, "Rober Downey Jr."))
    listCast.add(RecyclerViewICast(R.drawable.movie2, "Chris Evans"))
    listCast.add(RecyclerViewICast(R.drawable.movie3, "Mark Ruffalo"))
    listCast.add(RecyclerViewICast(R.drawable.movie4, "Chris Hemsworth"))


    return listCast
}
