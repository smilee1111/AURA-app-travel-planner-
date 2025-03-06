package com.example.aura_app.repository

import com.example.aura_app.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserRepositoryImpl : UserRepository {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var ref: DatabaseReference = database.reference.child("profile")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login success")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                val userModel = UserModel(userId, "Unknown User", email, password) // Default username
                addUserToDatabase(userId, userModel) { success, message ->
                    callback(success, message, userId)
                }
            } else {
                callback(false, it.exception?.message.toString(), "")
            }
        }
    }

    override fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        ref.child(userId).setValue(userModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Registration success")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Reset email sent successfully")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        auth.signOut()
        callback(true, "Logout successful")
    }

    override fun getUserById(userId: String, callback: (UserModel?) -> Unit) {
        ref.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userModel = snapshot.getValue(UserModel::class.java)
                callback(userModel)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }
}
