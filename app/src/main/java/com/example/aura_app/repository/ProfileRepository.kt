package com.example.aura_app.repository

import com.example.aura_app.model.ProfileModel

interface ProfileRepository {
    fun getProfile(): ProfileModel
    fun addProfileImage(imageUrl: String)
    fun updateProfileImage(imageUrl: String)
    fun deleteProfileImage()
    fun addCoverImage(imageUrl: String)
    fun updateCoverImage(imageUrl: String)
    fun deleteCoverImage()
    fun addHighlight(imageUrl: String)
    fun removeHighlight(imageUrl: String)
}