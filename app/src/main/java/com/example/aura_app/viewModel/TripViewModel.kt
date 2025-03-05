package com.example.aura_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aura_app.model.TripModel
import com.example.aura_app.repository.TripRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class TripViewModel(private val tripRepository: TripRepository) : ViewModel() {

    private val _trips = MutableLiveData<List<TripModel>>()
    val trips: LiveData<List<TripModel>> get() = _trips

    private val _operationStatus = MutableLiveData<Result>()
    val operationStatus: LiveData<Result> get() = _operationStatus

    // Data class for handling success/failure status with error messages
    data class Result(val success: Boolean, val message: String?)

    // Fetch trips for the currently logged-in user
    fun getTripsForCurrentUser() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {
            tripRepository.getTripsByUserId(currentUserId).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _trips.value = task.result ?: emptyList()  // Update with trips, or empty list if none found
                } else {
                    _trips.value = emptyList()  // Handle empty result
                    _operationStatus.value = Result(false, "Failed to fetch trips: ${task.exception?.message}")
                }
            }
        } else {
            _trips.value = emptyList()  // Handle case when user is not logged in
            _operationStatus.value = Result(false, "User is not logged in")
        }
    }

    // Add a new trip
    fun addTrip(trip: TripModel) {
        tripRepository.addTrip(trip).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _operationStatus.value = Result(true, "Trip added successfully!")
            } else {
                _operationStatus.value = Result(false, "Failed to add trip: ${task.exception?.message}")
            }
        }
    }

    // Update trip information
    fun updateTrip(trip: TripModel) {
        tripRepository.updateTrip(trip).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _operationStatus.value = Result(true, "Trip updated successfully!")
            } else {
                _operationStatus.value = Result(false, "Failed to update trip: ${task.exception?.message}")
            }
        }
    }

    // Delete a trip by its ID
    fun deleteTrip(tripId: String) {
        tripRepository.deleteTrip(tripId).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _operationStatus.value = Result(true, "Trip deleted successfully!")
            } else {
                _operationStatus.value = Result(false, "Failed to delete trip: ${task.exception?.message}")
            }
        }
    }
}
