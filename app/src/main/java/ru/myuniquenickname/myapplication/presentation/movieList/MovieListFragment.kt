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
import ru.myuniquenickname.myapplication.presentation.MainViewModel

class MovieListFragment : Fragment() {

    private var listener: TransactionsFragmentClicks? = null
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val mainViewModelList: MainViewModel by sharedViewModel()

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
        val adapterMovies = MoviesAdapter(clickListener)
        mainViewModelList.movieList.observe(
            this,
            {
                adapterMovies.submitList(it)
            }
        )
        val recyclerView = binding.recyclerMovie
        recyclerView.setHasFixedSize(true)
        changeSpanCount(recyclerView)
        recyclerView.adapter = adapterMovies
    }

    private fun changeVisibilityProgressBar() {
        mainViewModelList.loadingState.observe(
            this,
            {
                binding.progressBar.isVisible = it
            }
        )
    }

    private fun changeSpanCount(recyclerView: RecyclerView) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager =
                GridLayoutManager(context, Companion.SPAN_COUNT_ORIENTATION_PORTRAIT)
        } else {
            recyclerView.layoutManager =
                GridLayoutManager(context, SPAN_COUNT_ORIENTATION_LANDSCAPE)
        }
    }

    interface TransactionsFragmentClicks {
        fun replaceFragment()
    }

    private val clickListener = object : MoviesAdapter.OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            mainViewModelList.mutableMovie.value = movie
            listener?.replaceFragment()
        }
    }

    companion object {
        private const val SPAN_COUNT_ORIENTATION_PORTRAIT = 2
        private const val SPAN_COUNT_ORIENTATION_LANDSCAPE = 3
    }
}
