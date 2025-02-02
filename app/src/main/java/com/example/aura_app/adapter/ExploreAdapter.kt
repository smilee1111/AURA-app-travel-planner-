package com.example.aura_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R

class ExploreAdapter(
    private val context: Context,
    private val imageList: ArrayList<Int>,
    private val nameList: ArrayList<String>,
    private val descList: ArrayList<String>,
    private val ratingList: ArrayList<String>,
    private val costList: ArrayList<String>
) : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    class ExploreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgPart)
        val name: TextView = itemView.findViewById(R.id.nameText2)
        val ratingNum: TextView = itemView.findViewById(R.id.ratingNum)
        val costText: TextView = itemView.findViewById(R.id.costText)
        val abtPlace: TextView = itemView.findViewById(R.id.abtPlace)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sampledesign2, parent, false)
        return ExploreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        holder.image.setImageResource(imageList[position])
        holder.name.text = nameList[position]
        holder.ratingNum.text = ratingList[position]
        holder.costText.text = costList[position]
        holder.abtPlace.text = descList[position]
    }

    override fun getItemCount(): Int {
        return nameList.size
    }
}