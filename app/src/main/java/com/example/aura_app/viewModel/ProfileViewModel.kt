package com.example.aura_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aura_app.model.ProfileModel
import com.example.aura_app.repository.ProfileRepository
import com.example.aura_app.repository.ProfileRepositoryImpl
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfileViewModel : ViewModel() {
    val profileRepository: ProfileRepository = ProfileRepositoryImpl()
    private val _profile = MutableLiveData<ProfileModel>()
    val profile: LiveData<ProfileModel> get() = _profile

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        val repo = profileRepository as ProfileRepositoryImpl
        val userId = repo.auth.currentUser?.uid ?: return
        repo.database.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val updatedProfile = snapshot.getValue(ProfileModel::class.java) ?: ProfileModel()
                if (updatedProfile.username.isEmpty()) {
                    updatedProfile.username = repo.auth.currentUser?.displayName ?: "Anonymous"
                    repo.updateFirebase() // Ensure initial username sync
                }
                _profile.value = updatedProfile
                android.util.Log.d("ProfileVM", "Profile updated: $updatedProfile")
            }

            override fun onCancelled(error: DatabaseError) {
                android.util.Log.e("ProfileVM", "Load failed: ${error.message}")
            }
        })
    }

    // Rest of the methods remain the same
    fun addProfileImage(imageUrl: String) {
        profileRepository.addProfileImage(imageUrl)
        // No need to set _profile.value here; ValueEventListener will handle it
    }

    fun updateProfileImage(imageUrl: String) {
        profileRepository.updateProfileImage(imageUrl)
    }

    fun deleteProfileImage() {
        profileRepository.deleteProfileImage()
    }

    fun addCoverImage(imageUrl: String) {
        profileRepository.addCoverImage(imageUrl)
    }

    fun updateCoverImage(imageUrl: String) {
        profileRepository.updateCoverImage(imageUrl)
    }

    fun deleteCoverImage() {
        profileRepository.deleteCoverImage()
    }

    fun addHighlight(imageUrl: String) {
        profileRepository.addHighlight(imageUrl)
    }

    fun removeHighlight(imageUrl: String) {
        profileRepository.removeHighlight(imageUrl)
    }
}