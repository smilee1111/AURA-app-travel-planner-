package com.example.aura_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.ui.activity.DashboardActivity

class DashboardAdapter(
    private val context: Context,
    private val imageList: ArrayList<Int>,
    private val nameList: ArrayList<String>,
    private val descList: ArrayList<String>,
    private val priceList: ArrayList<String>,
    private val itemClickListener: DashboardActivity
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    // Define an interface for click events
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

        // Set onClickListener for the ImageView
        holder.image.setOnClickListener {
            itemClickListener.onImageClick(position)
        }
    }

    override fun getItemCount(): Int {
        return nameList.size
    }
}
