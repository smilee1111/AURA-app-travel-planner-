package com.example.aura_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aura_app.model.UserModel
import com.example.aura_app.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(private val repo: UserRepository) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password) { success, message ->
            if (success) {
                val userId = repo.getCurrentUser()?.uid
                userId?.let { fetchUserDetails(it) } // Fetch user details on login success
            }
            callback(success, message)
        }
    }

    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.signup(email, password) { success, message, userId ->
            if (success) fetchUserDetails(userId) // Fetch user details after signup
            callback(success, message, userId)
        }
    }

    private fun fetchUserDetails(userId: String) {
        repo.getUserById(userId) { user ->
            user?.let {
                _username.postValue(it.Name)
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return repo.getCurrentUser()
    }
    fun logout(callback: (Boolean, String) -> Unit) {
        // Pass the callback to the repository's logout method
        repo.logout(callback)
    }
    fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        repo.addUserToDatabase(userId, userModel, callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }


}
