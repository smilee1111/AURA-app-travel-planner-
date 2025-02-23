package com.example.aura_app.repository

import com.example.aura_app.model.DestinationModel

interface DestinationRepository {
    fun addDestinationToDatabase(destId: String, destinationModel: DestinationModel, callback: (Boolean, String) -> Unit)
    fun getAllDestination(callback: (List<DestinationModel>?, Boolean, String) -> Unit)
    fun uploadImage(imageUri: String, callback: (Boolean, String) -> Unit)
}
