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
    private val imageList = arrayListOf(R.drawable.ktm, R.drawable.swiss, R.drawable.thailand,R.drawable.italy,
        R.drawable.india,R.drawable.france,R.drawable.usa,)
    private val nameList = arrayListOf("Nepal, Kathmandu", "Switzerland, Bern", "Thailand,Bangkok","Italy,Rome","India,NewDelhi","France,Paris","USA,washington DC")
    private val descList = arrayListOf(
        "Nepal, a landlocked gem nestled in South Asia, is celebrated for its breathtaking mountains, lush green terrains, and rich cultural heritage. This small yet diverse country is a haven for nature lovers, adventurers, and spiritual seekers alike.",
        "Switzerland, known for its picturesque landscapes, is a country in Europe that is famous for its mountains, lakes and  villages.",
        "Thailand known for tropical beaches,royal palaces, ancient temples displaying figures of Buddha. ",
        "Italy, a European country with a long Mediterranean coastline, has left a powerful mark on Western culture and cuisine. Its capital, Rome, is home to the Vatican as well as landmark art and ancient ruins.",
        "France, in Western Europe, encompasses medieval cities, alpine villages and Mediterranean beaches. Paris, its capital, is famed for its fashion houses, classical art museums including the Louvre and monuments like the Eiffel Tower.",
        "The U.S. is a country of 50 states covering a vast swath of North America, with Alaska in the northwest and Hawaii extending the nation’s presence into the Pacific Ocean. Major Atlantic Coast cities are New York, a global finance and culture center, and capital Washington, DC"
    )
    private val ratingList = arrayListOf("4.9R", "4.7R", "4.5R","4.7R","4.2R","4.9R","4.9R")
    private val costList = arrayListOf("$500", "$1000", " $750", "$950","$550","$1000","$1500")

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