package ru.myuniquenickname.myapplication.presentation.movieList

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myuniquenickname.myapplication.databinding.ViewHolderMovieBinding
import ru.myuniquenickname.myapplication.domain.entity.Movie

class MoviesAdapter(
    private val clickListener: OnRecyclerItemClicked
) :
    ListAdapter<Movie, MoviesAdapter.MoviesViewHolder>(MovieDiffCallback()) {

    class MoviesViewHolder(val binding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderMovieBinding.inflate(layoutInflater, parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val itemMovies = getItem(position)
        holder.binding.apply {
            putPosterImage(root, itemMovies.poster, poster)
            ageFragmentList.text = itemMovies.minimumAge.toString() + "+"
            ratingBarFragmentList.rating = itemMovies.ratings / 2
            name.text = itemMovies.title
            genres.text = itemMovies.genres.joinToString { it.name }
            reviewsFragmentList.text = itemMovies.numberOfRatings.toString() + " REVIEWS"
            durationMin.text = itemMovies.runtime.toString() + " MIN"
            onClickLike(itemMovies.like, imageViewLike, itemMovies)
        }
        holder.itemView.setOnClickListener { clickListener.onClick(itemMovies) }
    }

    private fun onClickLike(isLike: Boolean, imageViewLike: ImageView, itemMovies: Movie) {
        if (isLike) {
            imageViewLike.imageTintMode = PorterDuff.Mode.SRC_IN
        } else {
            imageViewLike.imageTintMode = PorterDuff.Mode.DST_IN
        }
        imageViewLike.setOnClickListener {
            if (imageViewLike.imageTintMode == PorterDuff.Mode.SRC_IN) {
                imageViewLike.imageTintMode = PorterDuff.Mode.DST_IN
                itemMovies.like = false
            } else {
                imageViewLike.imageTintMode = PorterDuff.Mode.SRC_IN
                itemMovies.like = true
            }
        }
    }

    private fun putPosterImage(root: CardView, posterPath: String, poster: ImageView) {
        Glide
            .with(root)
            .load(posterPath)
            .into(poster)
    }

    interface OnRecyclerItemClicked {
        fun onClick(itemMovies: Movie)
    }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        (oldItem.numberOfRatings == newItem.numberOfRatings && oldItem.ratings == newItem.ratings)
}
