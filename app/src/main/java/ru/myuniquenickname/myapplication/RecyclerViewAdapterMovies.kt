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

    class RecyclerViewViewHolder( val viewHolderMovieBinding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(viewHolderMovieBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderMovieBinding.inflate(layoutInflater, parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val itemMovies = DataSource.listMovies[position]
        holder.viewHolderMovieBinding.maskFragmentList.setImageResource(itemMovies.imageResourceMaskList)
        holder.viewHolderMovieBinding.logoFragmentList.setImageResource(itemMovies.imageResourceLogoList)
        holder.viewHolderMovieBinding.ageFragmentList.text = itemMovies.age.toString() + "+"
        holder.viewHolderMovieBinding.name.text = itemMovies.name
        holder.viewHolderMovieBinding.tagFragmentList.text = itemMovies.tag
        holder.viewHolderMovieBinding.reviewsFragmentList.text = itemMovies.reviews.toString() + " REVIEWS"
        holder.viewHolderMovieBinding.durationMin.text = itemMovies.minutes.toString() + " MIN"
        holder.viewHolderMovieBinding.imageViewLikeEmpty.setOnClickListener { onClickLike(holder.viewHolderMovieBinding.imageViewLike, position) }
        holder.itemView.setOnClickListener { clickListener.onClick(itemMovies.id) }
        if (itemMovies.like) holder.viewHolderMovieBinding.imageViewLike.visibility = View.VISIBLE
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

    interface OnRecyclerItemClicked {
        fun onClick(id: Int)
        fun onClickLike(id: Int, flag: Boolean)
    }
}

