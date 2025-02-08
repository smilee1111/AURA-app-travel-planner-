package com.example.app.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aura_app.R
import java.text.SimpleDateFormat
import java.util.*

class CalenderFragment : Fragment() {

    private lateinit var btnPickDate: Button
    private lateinit var tvSelectedDate: TextView
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calender, container, false)

        btnPickDate = view.findViewById(R.id.btnPickDate)
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate)

        btnPickDate.setOnClickListener { showDatePicker() }

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
        val format = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
        tvSelectedDate.text = format.format(calendar.time)
    }
}
