package ru.myuniquenickname.myapplication.presentation.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesDetailsBinding

class MovieFragment : Fragment() {

    private var id: Long? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel by viewModel<MovieViewModel>()

    companion object {
        fun newInstance(id: Long) = MovieFragment().apply {
            arguments = Bundle().apply { putLong(ID, id) }
        }

        private const val ID = "ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getLong(ID)
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
        changeVisibilityProgressBar()
        updateContent()
        createRecyclerView()
    }

    private fun createRecyclerView() {
        id?.let { movieViewModel.loadActors(it) }
        movieViewModel.actors.observe(
            this,
            {
                binding.cast.visibility = View.INVISIBLE
                binding.cast.visibility = View.VISIBLE
                val recyclerView = binding.castRecyclerView
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = ActorsAdapter(it)
                recyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        )
    }

    private fun updateContent() {
        id?.let { movieViewModel.loadMovie(it) }
        movieViewModel.movie.observe(
            this,
            {
                binding.apply {
                    setPicture(logo, it.backdrop)
                    age.text = it.minimumAge.toString() + "+"
                    ratingBar.rating = it.ratings / 2
                    name.text = it.title
                    tag.text = it.genres
                    reviews.text = it.numberOfRatings.toString() + " REVIEWS"
                    storyLine.text = it.overview
                }
            }
        )
    }

    private fun setPicture(poster: ImageView, backdropPath: String) {
        context?.let {
            Glide
                .with(it)
                .load(backdropPath)
                .into(poster)
        }
    }

    private fun changeVisibilityProgressBar() {
        movieViewModel.loadingState.observe(
            this,
            {
                binding.progressBar.isVisible = it
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
