package ru.myuniquenickname.myapplication.presentation.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesDetailsBinding
import ru.myuniquenickname.myapplication.domain.entity.Actor
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.presentation.adapters.RecyclerViewAdapterActors
import ru.myuniquenickname.myapplication.presentation.ViewModelMovie


class FragmentMoviesDetails() : Fragment() {

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModelMovieList by sharedViewModel<ViewModelMovie>()

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
        viewModelMovieList.mutableMovie.value?.let { updateContent(it) }
        viewModelMovieList.mutableMovie.value?.actors?.let { checkActorListIsEmpty(it) }
    }

    private fun checkActorListIsEmpty(actors: List<Actor>) {
        if (actors.isEmpty()) {
            binding.cast.visibility = View.INVISIBLE
        } else {
            binding.cast.visibility = View.VISIBLE
            val recyclerView = binding.castRecyclerView
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = RecyclerViewAdapterActors(actors)
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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


