package com.example.aura_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.squareup.picasso.Picasso

class DashboardAdapter(
    private val context: Context,
    private var imageList: List<String>,
    private var nameList: List<String>,
    private var descList: List<String>,
    private var priceList: List<String>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.nameText)
        val desc: TextView = itemView.findViewById(R.id.descText)
        val price: TextView = itemView.findViewById(R.id.priceText)



    }
    fun updateData(
        newImages: ArrayList<String>,
        newNames: ArrayList<String>,
        newDescs: ArrayList<String>,
        newPrices: ArrayList<String>
    ) {
        this.imageList = newImages
        this.nameList = newNames
        this.descList = newDescs
        this.priceList = newPrices
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sampledesign, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val imageUrl = imageList[position]

        // Load the image using Picasso
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.pp)
            .error(R.drawable.pp)
            .into(holder.image)

        holder.name.text = nameList[position]
        holder.desc.text = descList[position]
        holder.price.text = priceList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = nameList.size
}
