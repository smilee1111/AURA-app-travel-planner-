package com.example.aura_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.adapter.DashboardAdapter

class HomeFragment : Fragment(), DashboardAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private val imageList = arrayListOf(R.drawable.ktm, R.drawable.boudha, R.drawable.thailand,R.drawable.italy,R.drawable.india)
    private val nameList = arrayListOf("Nepal, Kathmandu", "Switzerland, Bern", "Thailand")
    private val descList = arrayListOf(
        "Nepal it is mainly situated in the Himalayas, but also includes parts of the Indo Gangetic Plain.",
        "Home to numerous lakes, villages and the high peaks of the Alps. Its cities contain medieval quarters. " +
                "The country is also known for its ski resorts and hiking trails.",
        "Thailand known for tropical beaches, opulent royal palaces, ancient ruins and ornate temples displaying figures of Buddha. ",
        "Italy, a European country with a long Mediterranean coastline, has left a powerful mark on Western culture and cuisine.",
        "India is home to many popular destinations, including Agra, Amritsar, and the Andaman & Nicobar Islands"
    )
    private val priceList = arrayListOf("Starting from $500", "Starting from $1000", "Starting from $750","Starting from $950","Starting from $550")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = DashboardAdapter(requireContext(), imageList, nameList, descList, priceList, this)

        return view
    }

    override fun onImageClick(position: Int) {
        if (position == 0) { // KTM image is clicked
            // Handle the click event, e.g., replace fragment
        }
    }
}