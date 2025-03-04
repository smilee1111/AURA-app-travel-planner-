package com.example.aura_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aura_app.model.TripModel
import com.example.aura_app.repository.TripRepository
import com.example.aura_app.repository.TripRepositoryImpl
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {

    private val tripRepository: TripRepository = TripRepositoryImpl()

    private val _trips = MutableLiveData<List<TripModel>>()
    val trips: LiveData<List<TripModel>> get() = _trips

    private val _saveTripStatus = MutableLiveData<Boolean>()
    val saveTripStatus: LiveData<Boolean> get() = _saveTripStatus

    private val _deleteTripStatus = MutableLiveData<Boolean>()
    val deleteTripStatus: LiveData<Boolean> get() = _deleteTripStatus

    private val _updateTripStatus = MutableLiveData<Boolean>()
    val updateTripStatus: LiveData<Boolean> get() = _updateTripStatus

    // Create new trip
    fun saveTrip(trip: TripModel) {
        viewModelScope.launch {
            tripRepository.addTrip(trip).addOnSuccessListener {
                _saveTripStatus.value = true
            }.addOnFailureListener {
                _saveTripStatus.value = false
            }
        }
    }

    // Get trips by userId
    fun loadTripsByUserId(userId: String) {
        viewModelScope.launch {
            tripRepository.getTripsByUserId(userId).addOnSuccessListener { trips ->
                _trips.value = trips
            }.addOnFailureListener {
                _trips.value = emptyList() // Handle failure
            }
        }
    }

    // Update a trip
    fun updateTrip(trip: TripModel) {
        viewModelScope.launch {
            tripRepository.updateTrip(trip).addOnSuccessListener {
                _updateTripStatus.value = true
            }.addOnFailureListener {
                _updateTripStatus.value = false
            }
        }
    }

    // Delete a trip
    fun deleteTrip(tripId: String) {
        viewModelScope.launch {
            tripRepository.deleteTrip(tripId).addOnSuccessListener {
                _deleteTripStatus.value = true
            }.addOnFailureListener {
                _deleteTripStatus.value = false
            }
        }
    }
}
