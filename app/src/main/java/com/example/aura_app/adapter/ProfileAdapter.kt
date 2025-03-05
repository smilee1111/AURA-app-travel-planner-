package com.example.aura_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.squareup.picasso.Picasso

class ProfileAdapter(
    private var images: List<String>,
    private val onImageClickListener: (String) -> Unit, // Callback for image click
    private val onImageLongClickListener: (String) -> Unit // Callback for image long click
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

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

        holder.imageView.setOnClickListener {
            onImageClickListener(imageUrl) // Trigger the click event
        }

        holder.imageView.setOnLongClickListener {
            onImageLongClickListener(imageUrl) // Trigger the long click event
            true
        }
    }

    override fun getItemCount() = images.size
}
