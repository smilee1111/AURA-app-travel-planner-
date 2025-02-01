package com.example.aura_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.adapter.ExploreAdapter

class ExploreFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    // Sample data
    private val imageList = arrayListOf(R.drawable.ktm, R.drawable.boudha, R.drawable.himal)
    private val nameList = arrayListOf("Nepal, Kathmandu", "Switzerland, Bern", "Other Destination")
    private val descList = arrayListOf(
        "Nepal, a landlocked gem nestled in South Asia, is celebrated for its breathtaking mountains, lush green terrains, and rich cultural heritage. This small yet diverse country is a haven for nature lovers, adventurers, and spiritual seekers alike.",
        "Switzerland, known for its picturesque landscapes, is a country in Europe that is famous for its mountains, lakes, and charming villages.",
        "Other Destination, a place of wonder and beauty, offers unique experiences and adventures."
    )
    private val ratingList = arrayListOf("4.9R", "4.7R", "4.5R")
    private val costList = arrayListOf("$500", "$1000", " $750")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ExploreAdapter(requireContext(), imageList, nameList, descList, ratingList, costList)

        return view
    }
}