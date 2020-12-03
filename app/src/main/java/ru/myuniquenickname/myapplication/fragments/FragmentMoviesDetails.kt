package ru.myuniquenickname.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.RecyclerViewAdapter
import ru.myuniquenickname.myapplication.RecyclerViewICast
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesDetailsBinding


class FragmentMoviesDetails : Fragment() {

    private var _binding: FragmentMoviesDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.castRecyclerView
        recyclerView.setHasFixedSize(true)
        val adapter = RecyclerViewAdapter(createList())
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
