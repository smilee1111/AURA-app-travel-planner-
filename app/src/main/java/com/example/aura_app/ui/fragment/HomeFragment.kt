package com.example.aura_app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.adapter.DashboardAdapter

class HomeFragment : Fragment(), DashboardAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    private val imageList = arrayListOf(
        R.drawable.ktm, R.drawable.bern, R.drawable.thailand,
        R.drawable.italy, R.drawable.india, R.drawable.france, R.drawable.usa
    )
    private val nameList = arrayListOf(
        "Nepal, Kathmandu", "Switzerland, Bern", "Thailand, Bangkok",
        "Italy, Rome", "India, New Delhi", "France, Paris", "USA, Washington DC"
    )
    private val descList = arrayListOf(
        "Nepal is mainly situated in the Himalayas, but also includes parts of the Indo-Gangetic Plain.",
        "Home to numerous lakes, villages, and high peaks of Alps. Its cities contain medieval quarters.",
        "Thailand is known for tropical beaches, royal palaces, and ancient temples displaying figures of Buddha.",
        "Italy, a country with a long Mediterranean coastline, has a powerful mark on Western culture & cuisine.",
        "India is home to popular destinations, including Agra, Amritsar, and the Andaman & Nicobar Islands.",
        "France is famed for its fashion houses, art museums like the Louvre, and monuments like the Eiffel Tower.",
        "Popular places to visit in the USA include LA, Chicago, New York, San Francisco, and Las Vegas."
    )
    private val priceList = arrayListOf(
        "Starting from $500", "Starting from $1000", "Starting from $750",
        "Starting from $950", "Starting from $550", "Starting from $1000", "Starting from $1500"
    )

    private var filteredNames = ArrayList(nameList)
    private var filteredImages = ArrayList(imageList)
    private var filteredDescs = ArrayList(descList)
    private var filteredPrices = ArrayList(priceList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.RecyclerView)
        searchView = view.findViewById(R.id.SearchView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = DashboardAdapter(requireContext(), filteredImages, filteredNames, filteredDescs, filteredPrices, this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })

        return view
    }

    private fun filter(query: String?) {
        filteredNames.clear()
        filteredImages.clear()
        filteredDescs.clear()
        filteredPrices.clear()

        if (!query.isNullOrEmpty()) {
            for (i in nameList.indices) {
                if (nameList[i].contains(query, ignoreCase = true)) {
                    filteredNames.add(nameList[i])
                    filteredImages.add(imageList[i])
                    filteredDescs.add(descList[i])
                    filteredPrices.add(priceList[i])
                }
            }
        } else {
            filteredNames.addAll(nameList)
            filteredImages.addAll(imageList)
            filteredDescs.addAll(descList)
            filteredPrices.addAll(priceList)
        }

        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onImageClick(position: Int) {
        // Handle click events for items
    }
}
