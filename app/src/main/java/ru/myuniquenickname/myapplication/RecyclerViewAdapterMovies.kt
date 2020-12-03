package ru.myuniquenickname.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.myuniquenickname.myapplication.databinding.ViewHolderMovieBinding

class RecyclerViewAdapterMovies(private val clickListener: OnRecyclerItemClicked) :
    RecyclerView.Adapter<RecyclerViewAdapterMovies.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(viewHolderMovieBinding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(viewHolderMovieBinding.root) {
        var imageViewMask: ImageView = viewHolderMovieBinding.maskFragmentList
        var imageViewLogo: ImageView = viewHolderMovieBinding.logoFragmentList
        var textViewAge: TextView = viewHolderMovieBinding.ageFragmentList
        var textViewName: TextView = viewHolderMovieBinding.name
        var textViewTag: TextView = viewHolderMovieBinding.tagFragmentList
        var textViewReviews: TextView = viewHolderMovieBinding.reviewsFragmentList
        var textViewMinutes: TextView = viewHolderMovieBinding.durationMin
        var imageViewLike: ImageView = viewHolderMovieBinding.imageViewLike
        var imageViewLikeEmpty: ImageView = viewHolderMovieBinding.imageViewLikeEmpty


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderMovieBinding.inflate(layoutInflater, parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val itemMovies = DataSource.listMovies[position]
        holder.imageViewMask.setImageResource(itemMovies.imageResourceMaskList)
        holder.imageViewLogo.setImageResource(itemMovies.imageResourceLogoList)
        holder.textViewAge.text = "${itemMovies.age}+"
        holder.textViewName.text = itemMovies.name
        holder.textViewTag.text = itemMovies.tag
        holder.textViewReviews.text = "${itemMovies.reviews} REVIEWS"
        holder.textViewMinutes.text = "${itemMovies.minutes} MIN"
        holder.imageViewLikeEmpty.setOnClickListener { onClickLike(holder.imageViewLike, position) }
        holder.itemView.setOnClickListener { clickListener.onClick(itemMovies.id) }
        if (itemMovies.like) holder.imageViewLike.visibility = View.VISIBLE
    }

    private fun onClickLike(imageViewLike: ImageView, position: Int) {

        if (imageViewLike.isVisible) {
            imageViewLike.visibility = View.INVISIBLE
            clickListener.onClickLike(position, false)
        } else {
            imageViewLike.visibility = View.VISIBLE
            clickListener.onClickLike(position, true)
        }

    }

    override fun getItemCount(): Int = DataSource.listMovies.size


}

interface OnRecyclerItemClicked {
    fun onClick(id: Int)
    fun onClickLike(id: Int, flag: Boolean)
}