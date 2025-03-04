package com.example.aura_app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aura_app.R
import com.example.aura_app.databinding.ActivitySignupBinding
import com.example.aura_app.model.UserModel
import com.example.aura_app.repository.UserRepositoryImpl
import com.example.aura_app.viewModel.UserViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var userViewModel: UserViewModel

    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance()
        usersRef = database.reference.child("users")

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.signUpBtn.setOnClickListener {

            // Get user inputs
            val email = binding.Email.text.toString().trim()
            val username = binding.UsernameSignup.text.toString().trim()
            val password = binding.passS.text.toString().trim()

            // Validate input fields
            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Check if the role is selected
            val selectedRole = when (binding.radioGroupRole.checkedRadioButtonId) {
                R.id.radioAdmin -> "admin"
                R.id.radioUser -> "user"
                else -> {
                    Toast.makeText(this, "Please select a role", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }

            // Sanitize email to use it as a Firebase key (replace '.' with '_')
            val sanitizedEmail = email.replace(".", "_")

            // Call sign up method
            userViewModel.signup(email, password) { success, message, userId ->
                if (success) {
                    // Create a UserModel and add to Firebase
                    val userModel = UserModel(userId, username, email, selectedRole)
                    userViewModel.addUserToDatabase(sanitizedEmail, userModel) { success, message ->
                        if (success) {
                            Toast.makeText(this@SignupActivity, message, Toast.LENGTH_LONG).show()

                            // Navigate to LoginActivity after successful signup
                            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish() // Finish the current SignupActivity so the user cannot go back
                        } else {
                            Toast.makeText(this@SignupActivity, message, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this@SignupActivity, message, Toast.LENGTH_LONG).show()
                }
            }

            // Handle edge-to-edge view insets
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}
