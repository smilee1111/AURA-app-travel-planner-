package com.example.aura_app.repository

import com.example.aura_app.model.ItiineryModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

class ItiineryRepositoryImpl : ItiineryRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val itiineryRef: DatabaseReference = database.getReference("itineraries")

    override fun saveOrUpdateItiinery(itiinery: ItiineryModel): Task<Void> {
        // Save or update the itinerary by userId
        return itiineryRef.child(itiinery.userId).setValue(itiinery)
    }

    override fun getItiinery(userId: String): Task<ItiineryModel> {
        // Fetch the itinerary by userId
        val result = itiineryRef.child(userId).get()
        return result.continueWith { task ->
            if (task.isSuccessful) {
                task.result?.getValue(ItiineryModel::class.java)
            } else {
                null
            }
        }
    }
}
