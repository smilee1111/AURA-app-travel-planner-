package com.example.aura_app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView  // Changed to AppCompat SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.adapter.DashboardAdapter
import com.example.aura_app.model.DestinationModel
import com.example.aura_app.repository.DestinationRepositoryImpl
import com.example.aura_app.viewModel.DestinationViewModel

class HomeFragment : Fragment(), DashboardAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: DashboardAdapter
    private lateinit var viewModel: DestinationViewModel

    private var imageList = arrayListOf(
        "android.resource://com.example.aura_app/" + R.drawable.ktm,
        "android.resource://com.example.aura_app/" + R.drawable.bern,
        "android.resource://com.example.aura_app/" + R.drawable.thailand,
        "android.resource://com.example.aura_app/" + R.drawable.italy,
        "android.resource://com.example.aura_app/" + R.drawable.india,
        "android.resource://com.example.aura_app/" + R.drawable.france,
        "android.resource://com.example.aura_app/" + R.drawable.usa
    )

    private var nameList = arrayListOf(
        "Nepal, Kathmandu",
        "Switzerland, Bern",
        "Thailand, Bangkok",
        "Italy, Rome",
        "India, New Delhi",
        "France, Paris",
        "USA, Washington DC"
    )

    private var descList = arrayListOf(
        "Nepal is mainly situated in the Himalayas, but also includes parts of the Indo-Gangetic Plain.",
        "Home to numerous lakes, villages, and high peaks of Alps. Its cities contain medieval quarters.",
        "Thailand is known for tropical beaches, royal palaces, and ancient temples displaying figures of Buddha.",
        "Italy, a country with a long Mediterranean coastline, has a powerful mark on Western culture & cuisine.",
        "India is home to popular destinations, including Agra, Amritsar, and the Andaman & Nicobar Islands.",
        "France is famed for its fashion houses, art museums like the Louvre, and monuments like the Eiffel Tower.",
        "Popular places to visit in the USA include LA, Chicago, New York, San Francisco, and Las Vegas."
    )

    private var priceList = arrayListOf(
        "Starting from $500",
        "Starting from $1000",
        "Starting from $750",
        "Starting from $950",
        "Starting from $550",
        "Starting from $1000",
        "Starting from $1500"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.RecyclerView)
        searchView = view.findViewById(R.id.SearchView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with initial data
        adapter = DashboardAdapter(requireContext(), imageList, nameList, descList, priceList, this)
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

        val repository = DestinationRepositoryImpl()
        viewModel = DestinationViewModel(repository)

        observeDestinations()

        return view
    }

    private fun observeDestinations() {
        viewModel.getAllDestination()
        viewModel.allProducts.observe(viewLifecycleOwner) { destinations ->
            updateRecyclerView(destinations)
        }
    }

    private fun filter(query: String?) {
        val filteredImages = ArrayList<String>()
        val filteredNames = ArrayList<String>()
        val filteredDescs = ArrayList<String>()
        val filteredPrices = ArrayList<String>()

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

        // Update adapter with filtered data
        adapter.updateData(filteredImages, filteredNames, filteredDescs, filteredPrices)
    }

    private fun updateRecyclerView(destinations: List<DestinationModel>) {
        imageList.clear()
        nameList.clear()
        descList.clear()
        priceList.clear()

        for (destination in destinations) {
            imageList.add(destination.destImageUrl)
            nameList.add(destination.destName)
            descList.add(destination.destDetail)
            priceList.add(destination.destCost)
        }

        // Update adapter with new data
        adapter.updateData(imageList, nameList, descList, priceList)
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}