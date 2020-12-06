package ru.myuniquenickname.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.myuniquenickname.myapplication.databinding.ViewHolderActorBinding

class RecyclerViewAdapterActors(private val castList: List<RecyclerViewItemActors>) :
    RecyclerView.Adapter<RecyclerViewAdapterActors.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(val binding: ViewHolderActorBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderActorBinding.inflate(layoutInflater, parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val castItem = this.castList[position]
        holder.binding.apply {
            imageActor.setImageResource(castItem.imageResource)
            textActor.text = castItem.name }
    }

    override fun getItemCount(): Int = castList.size
}