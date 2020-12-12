package ru.myuniquenickname.myapplication.fragments

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.RecyclerViewAdapterMovies
import ru.myuniquenickname.myapplication.data.Genre
import ru.myuniquenickname.myapplication.data.Movie
import ru.myuniquenickname.myapplication.data.loadMovies
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesListBinding


class FragmentMoviesList : Fragment() {

    private var listener: TransactionsFragmentClicks? = null
    private var _binding: FragmentMoviesListBinding? = null
    private var listMovies: List<Movie>? = null

    private val binding get() = _binding!!


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

        runBlocking {
            listMovies = context?.let { loadMovies(it) }
        }

            val recyclerView = binding.recyclerMovie
            recyclerView.setHasFixedSize(true)
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.layoutManager = GridLayoutManager(context, 2)
            } else {
                recyclerView.layoutManager = GridLayoutManager(context, 3)
            }
            recyclerView.adapter = RecyclerViewAdapterMovies(clickListener, listMovies)

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

    interface TransactionsFragmentClicks {
        fun replaceFragment(id: Int)
    }

    private val clickListener = object : RecyclerViewAdapterMovies.OnRecyclerItemClicked {
        override fun onClick(id: Int) {
            listener?.replaceFragment(id)
        }

        override fun onClickLike(position: Int, flag: Boolean) {
            listMovies?.get(position)?.like = flag
        }

        override fun downloadAndSetPicture(poster: ImageView, posterPath: String) {
                Glide
                    .with(this@FragmentMoviesList)
                    .load(posterPath)
                    .into(poster)

        }

        override fun setGenres(genres: TextView, listGenres: List<Genre>) {
            var genresString : String? = null
            for (i in listGenres.indices){
                genresString += listGenres[i].name + ", "
            }
            genres.text = genresString
        }
    }

}