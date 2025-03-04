package com.example.aura_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.squareup.picasso.Picasso

class ProfileAdapter(private var images: List<String>) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    fun updateData(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewCountry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profilerecycle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = images[position]
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.pp)
            .error(R.drawable.imageplaceholder)
            .into(holder.imageView)
    }

    override fun getItemCount() = images.size
}