package com.example.aura_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R

class DashboardAdapter(
    private val context: Context,
    private val imageList: ArrayList<Int>,  // filtered image list
    private val nameList: ArrayList<String>,  // filtered name list
    private val descList: ArrayList<String>,  // filtered description list
    private val priceList: ArrayList<String>,  // filtered price list
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    interface OnItemClickListener {
        fun onImageClick(position: Int)
    }

    class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.nameText)
        val desc: TextView = itemView.findViewById(R.id.descText)
        val price: TextView = itemView.findViewById(R.id.priceText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sampledesign, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.image.setImageResource(imageList[position])
        holder.name.text = nameList[position]
        holder.desc.text = descList[position]
        holder.price.text = priceList[position]

        holder.image.setOnClickListener {
            itemClickListener.onImageClick(position)
        }
    }

    override fun getItemCount(): Int {
        return nameList.size
    }
}
