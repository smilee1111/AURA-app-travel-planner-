package com.example.aura_app.repository

import com.example.aura_app.model.TripModel
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.android.gms.tasks.Tasks

class TripRepositoryImpl : TripRepository {

    private val database = FirebaseDatabase.getInstance().reference
    private val tripsRef = database.child("trips")

    override fun addTrip(trip: TripModel): Task<Void> {
        val tripId = tripsRef.push().key ?: return Tasks.forException(Exception("Could not generate trip ID"))
        trip.id = tripId
        return tripsRef.child(tripId).setValue(trip)
    }

    override fun getTripsByUserId(userId: String): Task<List<TripModel>> {
        val trips = mutableListOf<TripModel>()
        val taskCompletionSource = com.google.android.gms.tasks.TaskCompletionSource<List<TripModel>>()

        tripsRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (tripSnapshot in snapshot.children) {
                    val trip = tripSnapshot.getValue(TripModel::class.java)
                    trip?.let { trips.add(it) }
                }
                taskCompletionSource.setResult(trips)
            }

            override fun onCancelled(error: DatabaseError) {
                taskCompletionSource.setException(error.toException())
            }
        })

        return taskCompletionSource.task
    }

    override fun updateTrip(trip: TripModel): Task<Void> {
        return tripsRef.child(trip.id).setValue(trip)
    }

    override fun deleteTrip(tripId: String): Task<Void> {
        return tripsRef.child(tripId).removeValue()
    }
}
