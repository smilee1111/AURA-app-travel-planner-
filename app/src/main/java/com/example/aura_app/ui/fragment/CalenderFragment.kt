package com.example.aura_app.ui.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.model.TripModel
import com.example.aura_app.ui.adapter.TripAdapter
import com.example.aura_app.viewmodel.TripViewModel
import com.example.aura_app.repository.TripRepositoryImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class CalenderFragment : Fragment() {

    private lateinit var rvTrips: RecyclerView
    private lateinit var fabAddTrip: FloatingActionButton
    private lateinit var tripViewModel: TripViewModel
    private val calendar = Calendar.getInstance()
    private lateinit var tripAdapter: TripAdapter
    private var currentUserId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calender, container, false)

        rvTrips = view.findViewById(R.id.rvTrips)
        fabAddTrip = view.findViewById(R.id.fabAddTrip)

        val tripRepository = TripRepositoryImpl()

        // Initialize the ViewModel
        tripViewModel = ViewModelProvider(this, TripViewModelFactory(tripRepository)).get(TripViewModel::class.java)

        // Get the current user ID
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        rvTrips.layoutManager = LinearLayoutManager(requireContext())
        tripAdapter = TripAdapter(
            onEditClick = { trip -> showEditTripDialog(trip) },
            onDeleteClick = { trip -> showDeleteTripDialog(trip) }
        )
        rvTrips.adapter = tripAdapter

        // Observe trip data
        tripViewModel.trips.observe(viewLifecycleOwner) { trips ->
            tripAdapter.submitList(trips)
        }

        // Observe operation status
        tripViewModel.operationStatus.observe(viewLifecycleOwner) { result ->
            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
        }

        fabAddTrip.setOnClickListener { showAddTripDialog() }

        currentUserId?.let {
            tripViewModel.getTripsForCurrentUser()
        } ?: Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()

        return view
    }

    private fun showAddTripDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_trip, null)
        val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val alertDialog = dialogBuilder.create()

        val btnPickDate = dialogView.findViewById<Button>(R.id.btnDialogPickDate)
        val tvSelectedDate = dialogView.findViewById<TextView>(R.id.tvDialogSelectedDate)
        val etTripDetail = dialogView.findViewById<EditText>(R.id.etDialogTripDetail)
        val btnSaveTrip = dialogView.findViewById<Button>(R.id.btnDialogSaveTrip)
        val btnCancelTrip = dialogView.findViewById<Button>(R.id.btnDialogCancelTrip)

        btnPickDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                        GregorianCalendar(year, month, dayOfMonth).time
                    )
                    tvSelectedDate.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        btnSaveTrip.setOnClickListener {
            val date = tvSelectedDate.text.toString()
            val detail = etTripDetail.text.toString()

            if (date == "Selected Date" || detail.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            currentUserId?.let { userId ->
                val trip = TripModel(detail = detail, date = date, userId = userId)
                tripViewModel.addTrip(trip) // Add the trip via ViewModel
                alertDialog.dismiss() // Close the dialog after submitting
                tripViewModel.getTripsForCurrentUser()
            } ?: Toast.makeText(requireContext(), "User not logged in!", Toast.LENGTH_SHORT).show()
        }

        btnCancelTrip.setOnClickListener { alertDialog.dismiss() }

        alertDialog.show()
    }

    private fun showEditTripDialog(trip: TripModel) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_trip, null)
        val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val alertDialog = dialogBuilder.create()

        val btnPickDate = dialogView.findViewById<Button>(R.id.btnDialogPickDate)
        val tvSelectedDate = dialogView.findViewById<TextView>(R.id.tvDialogSelectedDate)
        val etTripDetail = dialogView.findViewById<EditText>(R.id.etDialogTripDetail)
        val btnSaveTrip = dialogView.findViewById<Button>(R.id.btnDialogSaveTrip)
        val btnCancelTrip = dialogView.findViewById<Button>(R.id.btnDialogCancelTrip)

        // Pre-fill the data with existing trip information
        tvSelectedDate.text = trip.date
        etTripDetail.setText(trip.detail)

        btnPickDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                        GregorianCalendar(year, month, dayOfMonth).time
                    )
                    tvSelectedDate.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        btnSaveTrip.setOnClickListener {
            val date = tvSelectedDate.text.toString()
            val detail = etTripDetail.text.toString()

            if (date == "Selected Date" || detail.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedTrip = trip.copy(detail = detail, date = date)
            tripViewModel.updateTrip(updatedTrip) // Update the trip via ViewModel
            alertDialog.dismiss() // Close the dialog after updating
            tripViewModel.getTripsForCurrentUser()
        }

        btnCancelTrip.setOnClickListener { alertDialog.dismiss() }

        alertDialog.show()
    }

    private fun showDeleteTripDialog(trip: TripModel) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to delete this trip?")
            .setPositiveButton("Yes") { _, _ ->
                tripViewModel.deleteTrip(trip.id) // Delete the trip via ViewModel
                tripViewModel.getTripsForCurrentUser()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .create()

        alertDialog.show()
    }

    // ViewModel Factory class within the fragment itself
    class TripViewModelFactory(private val tripRepository: TripRepositoryImpl) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TripViewModel::class.java)) {
                return TripViewModel(tripRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
    }