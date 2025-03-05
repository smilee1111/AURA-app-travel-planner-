package com.example.aura_app.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepoImpl(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) : AuthRepo {

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Login successful")
            } else {
                callback(false, task.exception?.message ?: "Login failed")
            }
        }
    }

    override fun signup(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Signup successful")
            } else {
                callback(false, task.exception?.message ?: "Signup failed")
            }
        }
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        auth.signOut()
        callback(true, "Logout successful")
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Reset email sent successfully")
            } else {
                callback(false, task.exception?.message ?: "Password reset failed")
            }
        }
    }
}
