package ru.myuniquenickname.myapplication.presentation.movieList

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesListBinding

class MovieListFragment : Fragment() {

    private var listener: TransactionsFragmentClicks? = null
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val movieListViewModel: MovieListViewModel by viewModel()
    private lateinit var adapterMovies: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        adapterMovies = MoviesAdapter(clickListener)
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
        movieListViewModel.movieList.observe(
            this,
            {
                binding.spinner.visibility = View.VISIBLE
                adapterMovies.submitList(it)
            }
        )
        val recyclerView = binding.recyclerMovie
        recyclerView.setHasFixedSize(true)
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

        binding.editMovieText.doAfterTextChanged {
            lifecycleScope.launch {
                movieListViewModel.queryChannel.send(it.toString())
            }
        }
        movieListViewModel.moviesListSearch.observe(
            this,
            {
                when (it) {
                    is ValidResult -> {
                        adapterMovies.submitList(it.result)
                        binding.spinner.visibility = View.INVISIBLE
                    }
                    is ErrorResult -> {
                        val toast = Toast.makeText(
                            activity,
                            "Something went wrong when downloading movies. Please, check your connection and try again",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.TOP, 0, 0)
                        toast.show()
                    }
                    is EmptyResult -> {
                        val toast = Toast.makeText(
                            activity,
                            "No results matching your query",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.TOP, 0, 0)
                        toast.show()
                    }

                    is EmptyQuery -> {
                        movieListViewModel.loadPopularMovies()
                        val toast = Toast.makeText(
                            activity,
                            "Empty query",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.TOP, 0, 0)
                        toast.show()
                    }
                    is TerminalError -> {
                        val toast = Toast.makeText(
                            activity,
                            "Unexpected error while trying to fetch movies!",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.TOP, 0, 0)
                        toast.show()
                    }
                }
            }
        )
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
