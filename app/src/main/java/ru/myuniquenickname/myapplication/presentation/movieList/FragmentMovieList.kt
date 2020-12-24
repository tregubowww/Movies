package ru.myuniquenickname.myapplication.presentation.movieList

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesListBinding
import ru.myuniquenickname.myapplication.domain.entity.Movie
import ru.myuniquenickname.myapplication.presentation.ViewModelMovie
import ru.myuniquenickname.myapplication.presentation.adapters.RecyclerViewAdapterMovies


class FragmentMovieList : Fragment() {

    private var listener: TransactionsFragmentClicks? = null
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val viewModelMovieList: ViewModelMovie by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeVisibilityProgressBar()
        createRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TransactionsFragmentClicks) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun createRecyclerView() {
        val adapterMovies = RecyclerViewAdapterMovies(clickListener)
        viewModelMovieList.movieList.observe(this, {
            adapterMovies.submitList(it)
        })
        val recyclerView = binding.recyclerMovie
        recyclerView.setHasFixedSize(true)
        changeSpanCount(recyclerView)
        recyclerView.adapter = adapterMovies
    }

    private fun changeVisibilityProgressBar() {
        viewModelMovieList.loadingState.observe(this, {
            binding.progressBar.isVisible = it
        })
    }

    private fun changeSpanCount(recyclerView: RecyclerView) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 3)
        }
    }

    interface TransactionsFragmentClicks {
        fun replaceFragment()
    }

    private val clickListener = object : RecyclerViewAdapterMovies.OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            viewModelMovieList.mutableMovie.value = movie
            listener?.replaceFragment()
        }
    }
}