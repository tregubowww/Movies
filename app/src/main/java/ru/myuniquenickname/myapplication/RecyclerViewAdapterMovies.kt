package ru.myuniquenickname.myapplication

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.myuniquenickname.myapplication.databinding.ViewHolderMovieBinding

class RecyclerViewAdapterMovies(private val clickListener: OnRecyclerItemClicked) :
    RecyclerView.Adapter<RecyclerViewAdapterMovies.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder( val binding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderMovieBinding.inflate(layoutInflater, parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val itemMovies = DataSource.listMovies[position]
        holder.binding.apply {
            maskFragmentList.setImageResource(itemMovies.imageResourceMaskList)
            maskFragmentList.setImageResource(itemMovies.imageResourceMaskList)
            logoFragmentList.setImageResource(itemMovies.imageResourceLogoList)
            ageFragmentList.text = itemMovies.age.toString() + "+"
            name.text = itemMovies.name
            tagFragmentList.text = itemMovies.tag
            reviewsFragmentList.text = itemMovies.reviews.toString() + " REVIEWS"
            durationMin.text = itemMovies.minutes.toString() + " MIN"
            if (itemMovies.like)
                imageViewLike.imageTintMode = PorterDuff.Mode.SRC_IN
            else
                imageViewLike.imageTintMode = PorterDuff.Mode.DST_IN
            imageViewLike.setOnClickListener { onClickLike(imageViewLike, position) }
        }
        holder.itemView.setOnClickListener { clickListener.onClick(itemMovies.id) }
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

    override fun getItemCount(): Int = DataSource.listMovies.size

    interface OnRecyclerItemClicked {
        fun onClick(id: Int)
        fun onClickLike(id: Int, flag: Boolean)
    }
}

