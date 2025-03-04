package com.example.aura_app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aura_app.databinding.ActivityLoginBinding
import com.example.aura_app.viewModel.UserViewModel
import com.example.aura_app.R
import com.example.aura_app.repository.UserRepositoryImpl
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityLoginBinding

    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance()
        usersRef = database.reference.child("users")

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.login.setOnClickListener {
            val email = binding.LoginEmail.text.toString().trim()
            val password = binding.password.editText?.text.toString().trim()

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Check if password is empty
            if (password.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Perform login
            userViewModel.login(email, password) { success, message ->
                if (success) {
                    // Sanitize email to avoid Firebase path issues
                    val sanitizedEmail = email.replace(".", "_")

                    // Query Firebase using sanitized email
                    usersRef.child(sanitizedEmail).get().addOnSuccessListener { dataSnapshot ->
                        if (dataSnapshot.exists()) {
                            val role = dataSnapshot.child("role").value.toString()

                            // Navigate based on role
                            val intent = when (role) {
                                "admin" -> Intent(this@LoginActivity, AdminActivity::class.java)
                                "user" -> Intent(this@LoginActivity, DashboardActivity::class.java)
                                else -> {
                                    Toast.makeText(this@LoginActivity, "Invalid role", Toast.LENGTH_LONG).show()
                                    return@addOnSuccessListener
                                }
                            }
                            startActivity(intent)
                            finish()  // Finish the LoginActivity to prevent going back to login after successful login
                        } else {
                            Toast.makeText(this, "No user found with the provided email", Toast.LENGTH_LONG).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to get user data", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
