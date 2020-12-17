package ru.myuniquenickname.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.myuniquenickname.myapplication.RecyclerViewAdapterActors
import ru.myuniquenickname.myapplication.data.Movie
import ru.myuniquenickname.myapplication.data.loadMovies
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesDetailsBinding

const val ID_KEY = "ID_movie"

class FragmentMoviesDetails() : Fragment() {

    private var id: Int? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(id: Int) = FragmentMoviesDetails().apply {
            arguments = Bundle().apply { putInt(ID_KEY, id) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(ID_KEY)
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
        CoroutineScope(Dispatchers.Main).launch {
            val listMovies = context?.let { loadMovies(it) }
            val recyclerViewItemMovie = listMovies?.find { it.id == id }
            if (recyclerViewItemMovie != null) {
                updateContent(recyclerViewItemMovie)
                if (recyclerViewItemMovie.actors.isEmpty()) {
                    binding.cast.visibility = View.INVISIBLE
                } else {
                    binding.cast.visibility = View.VISIBLE
                    val recyclerView = binding.castRecyclerView
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter =  RecyclerViewAdapterActors(recyclerViewItemMovie.actors)
                    recyclerView.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    private fun updateContent(item: Movie) {
        binding.apply {
            setPicture(logo, item.backdrop)
            age.text = item.minimumAge.toString() + "+"
            ratingBar.rating = item.ratings / 2
            name.text = item.title
            tag.text = item.genres.joinToString { it.name }
            reviews.text = item.numberOfRatings.toString() + " REVIEWS"
            storyLine.text = item.overview
        }
    }

    private fun setPicture(poster: ImageView, backdropPath: String) {
        context?.let {
            Glide
                .with(it)
                .load(backdropPath)
                .into(poster)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


