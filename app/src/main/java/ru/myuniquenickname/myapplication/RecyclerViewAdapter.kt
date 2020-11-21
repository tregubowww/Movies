package ru.myuniquenickname.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val castList: List<RecyclerViewICast>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>() {
    class RecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageCast)
        var textView1: TextView = itemView.findViewById(R.id.textCast)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cast_item,
                parent, false)
        return RecyclerViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val castItem  = this.castList[position]
        holder.imageView.setImageResource(castItem.imageResource)
        holder.textView1.text = castItem.name
    }

    override fun getItemCount(): Int = castList.size
}