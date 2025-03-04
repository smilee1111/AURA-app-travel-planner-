package com.example.aura_app.repository

import com.example.aura_app.model.TripModel
import com.google.android.gms.tasks.Task

interface TripRepository {
    fun addTrip(trip: TripModel): Task<Void>
    fun getTripsByUserId(userId: String): Task<List<TripModel>>
    fun updateTrip(trip: TripModel): Task<Void>
    fun deleteTrip(tripId: String): Task<Void>
}
