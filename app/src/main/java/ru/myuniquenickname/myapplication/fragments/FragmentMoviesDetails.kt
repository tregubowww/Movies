package ru.myuniquenickname.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.myuniquenickname.myapplication.RecyclerViewAdapterActors
import ru.myuniquenickname.myapplication.data.Genre
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
        runBlocking {
            val listMovies: List<Movie>? = context?.let { loadMovies(it) }
            val recyclerViewItemMovie = listMovies?.find { it.id == id }

            if (recyclerViewItemMovie != null) {
                updateContent(recyclerViewItemMovie)
                val recyclerView = binding.castRecyclerView
                recyclerView.setHasFixedSize(true)
                val adapter = RecyclerViewAdapterActors(recyclerViewItemMovie.actors)
                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = layoutManager
            }
        }

    }

    private fun updateContent(item: Movie) {
        binding.apply {
            setPicture(logo, item.backdrop )
            age.text = item.minimumAge.toString() + "+"
            ratingBar.rating = item.ratings
            name.text = item.title
            setGenres(tag, item.genres)
            reviews.text = item.numberOfRatings.toString() + " REVIEWS"
            storyLine.text = item.overview
        }
    }
    private fun setPicture(poster: ImageView, backdropPath: String) {

        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                Glide
                    .with(it)
                    .load(backdropPath)
                    .into(poster)
            }
        }
    }
    fun setGenres(genres: TextView, listGenres: List<Genre>) {
        var genresString : String? = null
        for (i in listGenres.indices){
            genresString += listGenres[i].name + ", "
        }
        genres.text = genresString
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


