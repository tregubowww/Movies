package ru.myuniquenickname.myapplication

import android.graphics.PorterDuff
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import ru.myuniquenickname.myapplication.data.Genre
import ru.myuniquenickname.myapplication.data.Movie
import ru.myuniquenickname.myapplication.databinding.ViewHolderMovieBinding
import ru.myuniquenickname.myapplication.fragments.FragmentMoviesList

class RecyclerViewAdapterMovies(private val clickListener: OnRecyclerItemClicked, private val listMovies: List<Movie>?) :
    RecyclerView.Adapter<RecyclerViewAdapterMovies.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder( val binding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderMovieBinding.inflate(layoutInflater, parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        if  (listMovies != null) {
            val itemMovies = listMovies[position]
            holder.binding.apply {
                clickListener.downloadAndSetPicture(poster, itemMovies.poster)

                ageFragmentList.text = itemMovies.minimumAge.toString() + "+"
                name.text = itemMovies.title
                clickListener.setGenres(genres,itemMovies.genres)
                reviewsFragmentList.text = itemMovies.numberOfRatings.toString() + " REVIEWS"
                durationMin.text = itemMovies.runtime.toString() + " MIN"
                if (itemMovies.like)
                    imageViewLike.imageTintMode = PorterDuff.Mode.SRC_IN
                else
                    imageViewLike.imageTintMode = PorterDuff.Mode.DST_IN
                imageViewLike.setOnClickListener { onClickLike(imageViewLike, position) }
            }
            holder.itemView.setOnClickListener { clickListener.onClick(itemMovies.id) }
        }
    }

    private fun onClickLike(imageViewLike: ImageView, position: Int) {

        if (imageViewLike.imageTintMode == PorterDuff.Mode.SRC_IN) {
            imageViewLike.imageTintMode = PorterDuff.Mode.DST_IN
            clickListener.onClickLike(position, false)
        } else {
            imageViewLike.imageTintMode = PorterDuff.Mode.SRC_IN
            clickListener.onClickLike(position, true)
        }

    }

    override fun getItemCount(): Int = listMovies?.size ?: 0

    interface OnRecyclerItemClicked {
        fun onClick(id: Int)
        fun onClickLike(id: Int, flag: Boolean)
        fun downloadAndSetPicture(poster: ImageView, posterPath: String)
        fun setGenres(genres: TextView, listGenres: List<Genre>)
    }
}

