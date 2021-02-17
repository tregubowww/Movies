package ru.myuniquenickname.myapplication.presentation.movie_list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.data.work_manager.WorkConstraints
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesListBinding
import ru.myuniquenickname.myapplication.presentation.TransactionsFragmentClicks

class MovieListFragment : Fragment() {

    private var listener: TransactionsFragmentClicks? = null
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val movieListViewModel: MovieListViewModel by viewModel()
    private lateinit var adapterMovies: MoviesAdapter
    private val workConstraints = WorkConstraints()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            movieListViewModel.loadPopularMovies()
        }
        setHasOptionsMenu(true)
    }


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
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            PERIODIC_LOAD_MOVIES,
            ExistingPeriodicWorkPolicy.KEEP,
            workConstraints.constrainedRequest
        )
        initObservers()
        initViews()
    }

    private fun initViews() {

        val recyclerView = binding.recyclerMovie
        recyclerView.setHasFixedSize(true)
        changeSpanCount(recyclerView)
        recyclerView.adapter = adapterMovies
    }

    private fun initObservers() {
        loadingStateObserver()
        movieListObserver()
        movieListSearchObserver()
        typeMovieListObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.info_menu, menu)
        showSearchView(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_popular -> {
                movieListViewModel.loadPopularMovies()
                true
            }
            R.id.action_top -> {
                movieListViewModel.loadTopMovies()
                true
            }
            R.id.action_upcoming -> {
                movieListViewModel.loadUpcomingMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    private val clickListener = object : MoviesAdapter.OnRecyclerItemClicked {
        override fun onClick(id: Long) {
            listener?.replaceFragmentWithMovieDetails(id)
        }
    }

    private fun movieListSearchObserver() {
        movieListViewModel.moviesListSearch.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is ValidResult -> {
                        adapterMovies.submitList(it.result)
                    }
                    is ErrorResult -> {
                        toastShow(getString(R.string.errorResults))
                    }
                    is EmptyResult -> {
                        toastShow(getString(R.string.emptyResults))
                    }

                    is EmptyQuery -> {
                    }
                    is TerminalError -> {
                        toastShow(getString(R.string.terminalError))
                    }
                }
            }
        )
    }

    private fun movieListObserver() {
        movieListViewModel.movieList.observe(
            viewLifecycleOwner,
            {
                adapterMovies.submitList(it)
            }
        )
    }

    private fun typeMovieListObserver() {
        movieListViewModel.mutableTypeMovies.observe(
            viewLifecycleOwner,
            {
                binding.typeListTextView.text = it
            }
        )
    }

    private fun loadingStateObserver() {
        movieListViewModel.loadingState.observe(
            viewLifecycleOwner,
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

    private fun changeSpanCount(recyclerView: RecyclerView) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager =
                GridLayoutManager(context, SPAN_COUNT_ORIENTATION_PORTRAIT)
        } else {
            recyclerView.layoutManager =
                GridLayoutManager(context, SPAN_COUNT_ORIENTATION_LANDSCAPE)
        }
    }

    private fun toastShow(text: String?) {
        val toast = Toast.makeText(
            activity,
            text,
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    private fun showSearchView(menu: Menu) {
        val searchViewItem = menu.findItem(R.id.search)
        val searchView = searchViewItem.actionView as SearchView
        searchView.queryHint = "Search Movie..."
        searchView.setOnCloseListener {
            movieListObserver()
            binding.typeListTextView.isVisible = true
            false
        }
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    binding.typeListTextView.isVisible = false
                    lifecycleScope.launch {
                        movieListViewModel.queryChannel.send(newText)
                    }
                    return false
                }
            }
        )
    }

    companion object {
        private const val SPAN_COUNT_ORIENTATION_PORTRAIT = 2
        private const val SPAN_COUNT_ORIENTATION_LANDSCAPE = 3
        private const val PERIODIC_LOAD_MOVIES = "PERIODIC_LOAD_MOVIES"
    }
}
