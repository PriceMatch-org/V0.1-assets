package com.pricematch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pricematch.R
import com.pricematch.model.UpdateItem

class UpdatesCarouselAdapter(private val updateItems : List<UpdateItem>) : RecyclerView.Adapter<UpdatesCarouselAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
        val newsTitle: TextView = itemView.findViewById(R.id.newsTitle)
        val newsDescription: TextView = itemView.findViewById(R.id.newsDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_update_carousel, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = updateItems[position]
        holder.newsImage.setImageResource(item.imageResId) // Use Glide or Picasso for dynamic images
        holder.newsTitle.text = item.title
        holder.newsDescription.text = item.description
    }

    override fun getItemCount(): Int {
        return updateItems.size
    }
}