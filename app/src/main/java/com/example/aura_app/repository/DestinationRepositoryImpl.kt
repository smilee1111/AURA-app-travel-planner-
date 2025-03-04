package com.example.aura_app.repository

import com.example.aura_app.model.DestinationModel
import com.example.aura_app.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DestinationRepositoryImpl: DestinationRepository {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var ref: DatabaseReference = database.reference.child("destinations")

    override fun addDestinationToDatabase(
        destId: String,
        destinationModel: DestinationModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(destId).setValue(destinationModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "destination added successfully.")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun getAllDestination(callback: (List<DestinationModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var products = mutableListOf<DestinationModel>()
                    for(eachData in snapshot.children){
                        var model=eachData.getValue((DestinationModel::class.java))
                        if(model!=null){
                            products.add(model)
                        }
                    }
                    callback(products,true,"Product fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}