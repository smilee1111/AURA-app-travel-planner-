package com.example.app.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.aura_app.R
import java.text.SimpleDateFormat
import java.util.*

class CalenderFragment : Fragment() {

    private lateinit var btnPickDate: Button
    private lateinit var tvSelectedDate: TextView
    private lateinit var etTripDetail: EditText
    private lateinit var btnSaveTrip: Button
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calender, container, false)

        btnPickDate = view.findViewById(R.id.btnPickDate)
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate)
        etTripDetail = view.findViewById(R.id.etTripDetail)
        btnSaveTrip = view.findViewById(R.id.btnSaveTrip)

        btnPickDate.setOnClickListener { showDatePicker() }
        btnSaveTrip.setOnClickListener { saveTripDetails() }

        return view
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                updateDateText()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateText() {
        val format = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())
        tvSelectedDate.text = format.format(calendar.time)
    }

    private fun saveTripDetails() {
        val tripDetail = etTripDetail.text.toString().trim()
        val tripDate = tvSelectedDate.text.toString()

        if (tripDetail.isEmpty()){
            Toast.makeText(requireContext(), "Please enter trip details!", Toast.LENGTH_SHORT).show()
            return
        }

        // Save trip details (Here you can store in a database or shared preferences)
        Toast.makeText(requireContext(), "Trip saved: $tripDetail on $tripDate", Toast.LENGTH_SHORT).show()
    }
}
