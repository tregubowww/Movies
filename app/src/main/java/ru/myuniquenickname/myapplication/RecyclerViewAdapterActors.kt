package ru.myuniquenickname.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myuniquenickname.myapplication.data.Actor
import ru.myuniquenickname.myapplication.databinding.ViewHolderActorBinding

class RecyclerViewAdapterActors(private val castList: List<Actor>) :
    RecyclerView.Adapter<RecyclerViewAdapterActors.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(val binding: ViewHolderActorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderActorBinding.inflate(layoutInflater, parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val castItem = castList[position]
        holder.binding.apply {
            putPosterImage(root, castItem.picture, imageActor)
            textActor.text = castItem.name
        }
    }

    private fun putPosterImage(root: View, posterPath: String, poster: ImageView) {
        Glide
            .with(root)
            .load(posterPath)
            .into(poster)
    }

    override fun getItemCount(): Int = castList.size
}