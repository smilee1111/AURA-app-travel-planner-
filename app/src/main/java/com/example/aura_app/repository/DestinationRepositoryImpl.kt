package com.example.aura_app.repository

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.aura_app.model.DestinationModel
import com.google.firebase.database.*

import java.util.concurrent.Executors

class DestinationRepositoryImpl : DestinationRepository {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref: DatabaseReference = database.reference.child("destinations")

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dvo87hsdg",
            "api_key" to "543825759132539",
            "api_secret" to "YaoUN2uz6A45CWG5e7OjNBvy9pA"
        )
    )

    override fun addDestinationToDatabase(destId: String, destinationModel: DestinationModel, callback: (Boolean, String) -> Unit) {
        ref.child(destId).setValue(destinationModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Destination added successfully.")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun getAllDestination(callback: (List<DestinationModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val destinations = mutableListOf<DestinationModel>()
                    for (eachData in snapshot.children) {
                        val model = eachData.getValue(DestinationModel::class.java)
                        if (model != null) {
                            destinations.add(model)
                        }
                    }
                    callback(destinations, true, "Destinations fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun uploadImage(imageUri: String, callback: (Boolean, String) -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val uploadResult = cloudinary.uploader().upload(imageUri, ObjectUtils.emptyMap())
                val imageUrl = uploadResult["secure_url"] as String
                callback(true, imageUrl)
            } catch (e: Exception) {
                callback(false, e.message ?: "Image upload failed")
            }
        }
    }
}
