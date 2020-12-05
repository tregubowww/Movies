package ru.myuniquenickname.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ru.myuniquenickname.myapplication.DataSource
import ru.myuniquenickname.myapplication.RecyclerViewAdapterMovies
import ru.myuniquenickname.myapplication.databinding.FragmentMoviesListBinding


class FragmentMoviesList : Fragment() {

    private var listener: TransactionsFragmentClicks? = null
    private var _binding: FragmentMoviesListBinding? = null

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

        val recyclerView = binding.recyclerMovie
        recyclerView.setHasFixedSize(true)
        val adapter = RecyclerViewAdapterMovies(clickListener)
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter


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
            DataSource.listMovies[position].like = flag
        }
    }

}