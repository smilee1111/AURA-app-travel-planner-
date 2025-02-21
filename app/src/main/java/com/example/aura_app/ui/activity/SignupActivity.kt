package com.example.aura_app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aura_app.databinding.ActivitySignupBinding
import com.example.aura_app.model.UserModel
import com.example.aura_app.repository.UserRepositoryImpl
import com.example.aura_app.viewModel.UserViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userRepository: UserRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        binding.signUpButton.setOnClickListener {
            val username = binding.userS.text.toString().trim()
            val email = binding.textInputLayout2.editText?.text.toString().trim()
            val password = binding.passS.text.toString().trim()


            userViewModel.signup(email, password) { success, message, userId ->
                if (success) {
                    val userModel = UserModel(userId, username, email, password)
                    userViewModel.addUserToDatabase(userId, userModel) { dbSuccess, dbMessage ->
                        if (dbSuccess) {
                            Toast.makeText(this, "Registration successful", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "email already in use.", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
