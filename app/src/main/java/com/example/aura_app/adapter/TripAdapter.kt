package com.example.aura_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.model.TripModel

class TripAdapter(
    private val onEditClick: (TripModel) -> Unit,
    private val onDeleteClick: (TripModel) -> Unit
) : ListAdapter<TripModel, TripAdapter.TripViewHolder>(TripDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = getItem(position)
        holder.bind(trip)
        holder.bindListeners(trip)
    }

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTripDetail: TextView = itemView.findViewById(R.id.tvTripDetail)
        private val tvTripDate: TextView = itemView.findViewById(R.id.tvTripDate)
        private val btnEditTrip =itemView.findViewById<ImageButton>(R.id.btnEditTrip)
        private val btnDeleteTrip = itemView.findViewById<ImageButton>(R.id.btnDeleteTrip)

        fun bind(trip: TripModel) {
            tvTripDetail.text = trip.detail
            tvTripDate.text = trip.date
        }

        fun bindListeners(trip: TripModel) {
            btnEditTrip.setOnClickListener { onEditClick(trip) }
            btnDeleteTrip.setOnClickListener { onDeleteClick(trip) }
        }
    }

    class TripDiffCallback : DiffUtil.ItemCallback<TripModel>() {
        override fun areItemsTheSame(oldItem: TripModel, newItem: TripModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TripModel, newItem: TripModel): Boolean {
            return oldItem == newItem
        }
    }
}
