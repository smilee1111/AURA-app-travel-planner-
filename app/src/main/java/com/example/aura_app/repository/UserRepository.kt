package com.example.aura_app.repository

import com.example.aura_app.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)
    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit)
    fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit)
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit)
    fun getCurrentUser(): FirebaseUser?
    fun logout(callback: (Boolean, String) -> Unit) // NEW FUNCTION
}
