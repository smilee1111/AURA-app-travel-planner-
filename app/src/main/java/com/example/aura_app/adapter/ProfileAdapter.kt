package com.example.aura_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R

class ProfileAdapter(private val countryNames: List<String>, private val imageResources: List<Int>) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryImage: ImageView = itemView.findViewById(R.id.imageViewCountry)
        val countryName: TextView = itemView.findViewById(R.id.countriesName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profilerecycle, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.countryName.text = countryNames[position]
        holder.countryImage.setImageResource(imageResources[position])  // Set image without Picasso
    }

    override fun getItemCount() = countryNames.size
}
