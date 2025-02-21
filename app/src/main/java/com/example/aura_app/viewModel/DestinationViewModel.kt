package com.example.aura_app.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.aura_app.model.DestinationModel
import com.example.aura_app.model.UserModel
import com.example.aura_app.repository.DestinationRepository
import com.example.aura_app.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class DestinationViewModel(private val repo: DestinationRepository) {

    fun addDestinationToDatabase(userId: String, destinationModel: DestinationModel, callback: (Boolean, String) -> Unit) {
        repo.addDestinationToDatabase(userId, destinationModel, callback)
    }

        var _alldestination= MutableLiveData<List<DestinationModel>>()
    var allProducts = MutableLiveData<List<DestinationModel>>()
        get()= _alldestination

    fun getAllDestination(){
        repo.getAllDestination(){ destination, success, message ->
            if(success){
                _alldestination.value=destination

            }
        }

    }
}