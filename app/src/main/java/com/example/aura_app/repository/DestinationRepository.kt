package com.example.aura_app.repository

import com.example.aura_app.model.DestinationModel
import com.example.aura_app.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface DestinationRepository {
    fun addDestinationToDatabase(destId: String, destinationModel: DestinationModel, callback: (Boolean, String) -> Unit)
    fun getAllDestination(callback: (List<DestinationModel>?, Boolean, String) -> Unit)
}