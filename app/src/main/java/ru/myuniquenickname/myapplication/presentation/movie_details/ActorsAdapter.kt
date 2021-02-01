package ru.myuniquenickname.myapplication.presentation.movie_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myuniquenickname.myapplication.R
import ru.myuniquenickname.myapplication.databinding.ViewHolderActorBinding
import ru.myuniquenickname.myapplication.domain.entity.Actor

class ActorsAdapter(private val castList: List<Actor>) :
    RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    class ActorsViewHolder(val binding: ViewHolderActorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderActorBinding.inflate(layoutInflater, parent, false)
        return ActorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val castItem = castList[position]
        holder.binding.apply {
            putPosterImage(root, castItem.picturePath, imageActor)
            textActor.text = castItem.name
        }
    }

    private fun putPosterImage(root: View, posterPath: String, poster: ImageView) {
        Glide
            .with(root)
            .load(posterPath)
            .placeholder(R.drawable.ic_baseline_empty_actor_outline_24)
            .into(poster)
    }

    override fun getItemCount(): Int = castList.size
}
