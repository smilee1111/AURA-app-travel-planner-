package com.example.aura_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.ui.adapter.ProfileAdapter

class ProfileFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var profileAdapter: ProfileAdapter

    // Country names
    private val countryNames = listOf("Nepal", "France", "Thailand", "Italy", "India")


    private val countryImages = listOf(
        R.drawable.ktmp,
        R.drawable.france,
        R.drawable.thailand,
        R.drawable.italy,
        R.drawable.india
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        recyclerView = view.findViewById(R.id.ProfileRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        profileAdapter = ProfileAdapter(countryNames, countryImages)
        recyclerView.adapter = profileAdapter

        return view
    }
}
