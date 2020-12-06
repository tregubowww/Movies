package ru.myuniquenickname.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myuniquenickname.myapplication.DataSource
import ru.myuniquenickname.myapplication.RecyclerViewAdapterActors
import ru.myuniquenickname.myapplication.RecyclerViewItemMovie
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesDetailsBinding


class FragmentMoviesDetails() : Fragment() {

    private var id: Int? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(id: Int) = FragmentMoviesDetails().apply {
            arguments = Bundle().apply { putInt(DataSource.ID_KEY, id) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(DataSource.ID_KEY)
    }

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
        val recyclerViewItemMovie = DataSource.listMovies.find { it.id == id }
        if (recyclerViewItemMovie != null) {
            updateContent(recyclerViewItemMovie)
            val recyclerView = binding.castRecyclerView
            recyclerView.setHasFixedSize(true)
            val adapter = RecyclerViewAdapterActors(recyclerViewItemMovie.listActors)
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
        }

    }

    private fun updateContent(item: RecyclerViewItemMovie) {
        binding.apply {
        logo.setImageResource(item.imageResourceLogoDetails)
        mask.setImageResource(item.imageResourceMaskDetails)
        age.text = item.age.toString() + "+"
        ratingBar.rating = item.rating
        name.text = item.name
        tag.text = item.tag
        reviews.text = item.reviews.toString() + " REVIEWS"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


