package com.example.aura_app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.aura_app.databinding.FragmentItiineryBinding
import com.example.aura_app.model.ItiineryModel
import com.example.aura_app.viewmodel.ItiineryViewModel
import com.google.firebase.auth.FirebaseAuth

class ItiineryFragment : Fragment() {

    private lateinit var binding: FragmentItiineryBinding
    private val itiineryViewModel: ItiineryViewModel by activityViewModels()

    private var itiineryId: String = ""  // Unique ID for the itinerary
    private var currentUserId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItiineryBinding.inflate(inflater, container, false)
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        // Fetch the existing itinerary for the user
        itiineryViewModel.fetchItiinery(currentUserId ?: "").observe(viewLifecycleOwner, Observer { itiinery ->
            if (itiinery != null) {
                // Populate the fields with existing itinerary data
                binding.InputDestination.setText(itiinery.destination)
                binding.Plan1.setText(itiinery.plan1)
                binding.Plan2.setText(itiinery.plan2)
                binding.plan3.setText(itiinery.plan3)
                binding.plan4.setText(itiinery.plan4)
                itiineryId = itiinery.id
            }
        })

        // Handle saving or updating the itinerary when the user clicks the save button
        binding.btnSave.setOnClickListener {
            val destination = binding.InputDestination.text.toString()
            val plan1 = binding.Plan1.text.toString()
            val plan2 = binding.Plan2.text.toString()
            val plan3 = binding.plan3.text.toString()
            val plan4 = binding.plan4.text.toString()

            // Create the ItiineryModel object with the data
            val itiinery = ItiineryModel(
                id = itiineryId.ifEmpty { generateRandomId() },
                destination = destination,
                plan1 = plan1,
                plan2 = plan2,
                plan3 = plan3,
                plan4 = plan4,
                userId = currentUserId ?: ""
            )

            // Save or update the itinerary using the ViewModel
            itiineryViewModel.saveOrUpdateItiinery(itiinery).observe(viewLifecycleOwner, Observer { task ->
                if (task.isSuccessful) {
                    // Handle success (e.g., show a success message)
                } else {
                    // Handle failure (e.g., show an error message)
                }
            })
        }

        return binding.root
    }

    // A method to generate a random ID for new itineraries
    private fun generateRandomId(): String {
        return System.currentTimeMillis().toString() // You can customize this
    }
}
