package ru.myuniquenickname.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.myuniquenickname.myapplication.databinding.CastItemBinding

class RecyclerViewAdapter(private val castList: List<RecyclerViewICast>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(castItemBinding: CastItemBinding) :
        RecyclerView.ViewHolder(castItemBinding.root) {
        var imageView: ImageView = castItemBinding.imageCast
        var textView1: TextView = castItemBinding.textCast
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CastItemBinding.inflate(layoutInflater, parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val castItem = this.castList[position]
        holder.imageView.setImageResource(castItem.imageResource)
        holder.textView1.text = castItem.name
    }

    override fun getItemCount(): Int = castList.size
}