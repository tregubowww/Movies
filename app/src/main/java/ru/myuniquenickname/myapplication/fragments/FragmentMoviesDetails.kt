package ru.myuniquenickname.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myuniquenickname.myapplication.*
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesDetailsBinding


class FragmentMoviesDetails() : Fragment() {

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
        val id = arguments?.getInt(DataSource.ID)
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

    private fun updateContent(recyclerViewItemMovie: RecyclerViewItemMovie) {
        binding.logo.setImageResource(recyclerViewItemMovie.imageResourceLogoDetails)
        binding.mask.setImageResource(recyclerViewItemMovie.imageResourceMaskDetails)
        binding.age.text = recyclerViewItemMovie.age.toString() + "+"
        binding.ratingBar.rating = recyclerViewItemMovie.rating
        binding.name.text = recyclerViewItemMovie.name
        binding.tag.text = recyclerViewItemMovie.tag
        binding.reviews.text = recyclerViewItemMovie.reviews.toString() + " REVIEWS"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


