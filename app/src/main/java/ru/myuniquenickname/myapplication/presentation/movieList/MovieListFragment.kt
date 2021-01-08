package ru.myuniquenickname.myapplication.presentation.movieList

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesListBinding

class MovieListFragment : Fragment() {

    private var listener: TransactionsFragmentClicks? = null
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val movieListViewModel: MovieListViewModel by viewModel()

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
        loadMoviesAndCreateSpinner()
        createRecyclerView()
        search()
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
        movieListViewModel.movieList.observe(
            this,
            {
                adapterMovies.submitList(it)
            }
        )
        val recyclerView = binding.recyclerMovie
//        recyclerView.setHasFixedSize(true)
        changeSpanCount(recyclerView)
        recyclerView.adapter = adapterMovies
    }

    private fun changeVisibilityProgressBar() {
        movieListViewModel.loadingState.observe(
            this,
            {
                binding.progressBar.isVisible = it
            }
        )
    }

    private fun changeSpanCount(recyclerView: RecyclerView) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager =
                GridLayoutManager(context, SPAN_COUNT_ORIENTATION_PORTRAIT)
        } else {
            recyclerView.layoutManager =
                GridLayoutManager(context, SPAN_COUNT_ORIENTATION_LANDSCAPE)
        }
    }

    private fun loadMoviesAndCreateSpinner() {

        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.typeListMovies,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (parent.getItemAtPosition(position)) {
                    "Popular movies" -> movieListViewModel.loadPopularMovies()
                    "Top movies" -> movieListViewModel.loadTopMovies()
                    "Upcoming movies" -> movieListViewModel.loadUpcomingMovies()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun search() {
        binding.searchButton.setOnClickListener {
            val textSearch = binding.editMovieText.text.toString()
            if (textSearch.isNotEmpty()) {
                movieListViewModel.searchMovie(textSearch)
            }
        }
    }

    interface TransactionsFragmentClicks {
        fun replaceFragment(id: Long)
    }

    private val clickListener = object : MoviesAdapter.OnRecyclerItemClicked {
        override fun onClick(id: Long) {
            listener?.replaceFragment(id)
        }
    }

    companion object {
        private const val SPAN_COUNT_ORIENTATION_PORTRAIT = 2
        private const val SPAN_COUNT_ORIENTATION_LANDSCAPE = 3
    }
}
