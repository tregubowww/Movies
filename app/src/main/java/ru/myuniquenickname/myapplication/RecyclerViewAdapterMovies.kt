package ru.myuniquenickname.myapplication

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import ru.myuniquenickname.myapplication.data.Movie
import ru.myuniquenickname.myapplication.databinding.ViewHolderMovieBinding

class RecyclerViewAdapterMovies(
    private val clickListener: OnRecyclerItemClicked,
    private val listMovies: List<Movie>?
) :
    RecyclerView.Adapter<RecyclerViewAdapterMovies.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(val binding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderMovieBinding.inflate(layoutInflater, parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        if (listMovies!= null) {
            val itemMovies = listMovies[position]
            holder.binding.apply {
                putPosterImage(root, itemMovies.poster, poster)
                ageFragmentList.text = itemMovies.minimumAge.toString() + "+"
                ratingBarFragmentList.rating = itemMovies.ratings / 2
                name.text = itemMovies.title
                genres.text = itemMovies.genres.joinToString { it.name }
                reviewsFragmentList.text = itemMovies.numberOfRatings.toString() + " REVIEWS"
                durationMin.text = itemMovies.runtime.toString() + " MIN"
                onClickLike(itemMovies.like, imageViewLike, position)
            }
            holder.itemView.setOnClickListener { clickListener.onClick(itemMovies.id) }
        }
    }

    private fun onClickLike(isLike: Boolean, imageViewLike: ImageView, position: Int) {
        if (isLike) {
            imageViewLike.imageTintMode = PorterDuff.Mode.SRC_IN
        } else {
            imageViewLike.imageTintMode = PorterDuff.Mode.DST_IN
        }
        imageViewLike.setOnClickListener {
            if (imageViewLike.imageTintMode == PorterDuff.Mode.SRC_IN) {
                imageViewLike.imageTintMode = PorterDuff.Mode.DST_IN
                listMovies?.get(position)?.like = false
            }else {
                    imageViewLike.imageTintMode = PorterDuff.Mode.SRC_IN
                    listMovies?.get(position)?.like = true
                }
            }
        }

    private fun putPosterImage(root: CardView, posterPath: String, poster: ImageView) {
        Glide
            .with(root)
            .load(posterPath)
            .into(poster)
    }

    override fun getItemCount(): Int = listMovies?.size ?: 0

    interface OnRecyclerItemClicked {
        fun onClick(id: Int)
    }
}

