package com.example.aura_app.repository

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.aura_app.model.ProfileModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.Executors

class ProfileRepositoryImpl : ProfileRepository {
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().reference.child("users")
    private val userRepository = UserRepositoryImpl() // Add dependency
    private var profile = ProfileModel()

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dvo87hsdg",
            "api_key" to "543825759132539",
            "api_secret" to "YaoUN2uz6A45CWG5e7OjNBvy9pA"
        )
    )

    init {
        loadProfile()
    }
    private fun loadProfile() {
        val userId = auth.currentUser?.uid ?: return
        database.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profile = snapshot.getValue(ProfileModel::class.java) ?: ProfileModel()
                // Fetch user data to update username
                userRepository.getUserById(userId) { userModel ->
                    if (userModel != null && profile.username != userModel.Name) {
                        profile.username = userModel.Name.ifEmpty { "Anonymous" }
                        updateFirebase() // Sync updated username
                    }
                    android.util.Log.d("ProfileRepo", "Loaded profile: $profile")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                android.util.Log.e("ProfileRepo", "Load failed: ${error.message}")
            }
        })
    }

    override fun getProfile(): ProfileModel = profile
    private fun uploadToCloudinary(imageUri: String, callback: (Boolean, String) -> Unit) {
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

    fun updateFirebase() {
        val userId = auth.currentUser?.uid ?: return
        database.child(userId).setValue(profile)
    }

    override fun addProfileImage(imageUrl: String) {
        profile.profileImageUrl = imageUrl
        updateFirebase()
    }

    override fun updateProfileImage(imageUrl: String) {
        profile.profileImageUrl = imageUrl
        updateFirebase()
    }

    override fun deleteProfileImage() {
        profile.profileImageUrl = ""
        updateFirebase()
    }

    override fun addCoverImage(imageUrl: String) {
        profile.coverImageUrl = imageUrl
        updateFirebase()
    }

    override fun updateCoverImage(imageUrl: String) {
        profile.coverImageUrl = imageUrl
        updateFirebase()
    }

    override fun deleteCoverImage() {
        profile.coverImageUrl = ""
        updateFirebase()
    }

    override fun addHighlight(imageUrl: String) {
        profile.highlights = profile.highlights + imageUrl
        profile.posts = profile.highlights.size
        updateFirebase()
    }

    override fun removeHighlight(imageUrl: String) {
        profile.highlights = profile.highlights - imageUrl
        profile.posts = profile.highlights.size
        updateFirebase()
    }

    fun uploadProfileImage(imageUri: String) {
        uploadToCloudinary(imageUri) { success, result ->
            if (success) {
                addProfileImage(result)
                android.util.Log.d("ProfileRepo", "Profile image uploaded: $result")
            } else {
                android.util.Log.e("ProfileRepo", "Upload failed: $result")
            }
        }
    }

    fun uploadCoverImage(imageUri: String) {
        uploadToCloudinary(imageUri) { success, result ->
            if (success) {
                addCoverImage(result)
                android.util.Log.d("ProfileRepo", "Cover image uploaded: $result")
            } else {
                android.util.Log.e("ProfileRepo", "Upload failed: $result")
            }
        }
    }

    fun uploadHighlight(imageUri: String) {
        uploadToCloudinary(imageUri) { success, result ->
            if (success) {
                addHighlight(result)
                android.util.Log.d("ProfileRepo", "Highlight uploaded: $result")
            } else {
                android.util.Log.e("ProfileRepo", "Upload failed: $result")
            }
        }
    }
}