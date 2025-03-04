package com.example.aura_app.ui.fragment

import android.graphics.pdf.content.PdfPageGotoLinkContent.Destination
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
import com.example.aura_app.model.DestinationModel
import com.example.aura_app.repository.DestinationRepositoryImpl
import com.example.aura_app.viewModel.DestinationViewModel

class HomeFragment : Fragment(), DashboardAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: DashboardAdapter
    private lateinit var viewModel: DestinationViewModel

    private var imageList = ArrayList<String>()
    private var nameList = ArrayList<String>()
    private var descList = ArrayList<String>()
    private var priceList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

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

        adapter = DashboardAdapter(requireContext(), imageList, nameList, descList, priceList, this)
        recyclerView.adapter = adapter

        observeDestinations()

        return view
    }

    private fun observeDestinations() {
        viewModel.getAllDestination()
        viewModel.allProducts.observe(viewLifecycleOwner) { destinations ->
            updateRecyclerView(destinations)
        }
    }
    private var filteredNames = ArrayList(nameList)
    private var filteredImages = ArrayList(imageList)
    private var filteredDescs = ArrayList(descList)
    private var filteredPrices = ArrayList(priceList)
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

        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}
