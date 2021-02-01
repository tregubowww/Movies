package ru.myuniquenickname.myapplication.presentation.movie_details

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesDetailsBinding
import ru.myuniquenickname.myapplication.presentation.movie_list.LoadingState

class MovieFragment : Fragment() {

    private var id: Long? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel by viewModel<MovieViewModel>()
    private lateinit var recyclerView: RecyclerView

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
        createRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        movieObserver()
        loadingStateObserver()
        actorListObserver()
    }
    private fun createRecyclerView() {
        recyclerView = binding.castRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun actorListObserver() {
        id?.let { movieViewModel.loadActors(it) }
        movieViewModel.actors.observe(
            this,
            {
                binding.cast.isVisible = true
                recyclerView.adapter = ActorsAdapter(it)
            }
        )
    }

    private fun movieObserver() {
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

    private fun setPicture(poster: ImageView, backdropPath: String?) {
        context?.let {
            Glide
                .with(it)
                .load(backdropPath)
                .into(poster)
        }
    }

    private fun loadingStateObserver() {
        movieViewModel.loadingState.observe(
            this,
            {
                when (it.status) {
                    LoadingState.Status.FAILED -> {
                        toastShow(getString(R.string.loading_error))
                        binding.progressBar.isVisible = false
                    }
                    LoadingState.Status.RUNNING -> binding.progressBar.isVisible = true
                    LoadingState.Status.SUCCESS -> binding.progressBar.isVisible = false
                }
            }
        )
    }

    private fun toastShow(text: String?) {
        val toast = Toast.makeText(
            activity,
            text,
            Toast.LENGTH_LONG
        )
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }
}
