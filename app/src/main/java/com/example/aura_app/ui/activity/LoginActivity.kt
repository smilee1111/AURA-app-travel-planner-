package com.example.aura_app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aura_app.databinding.ActivityLoginBinding
import com.example.aura_app.viewModel.UserViewModel
import com.example.aura_app.R
import com.example.aura_app.repository.UserRepositoryImpl

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.login.setOnClickListener {
            val email = binding.LoginEmail.text.toString().trim() // Fixed the reference to LoginEmail
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

            userViewModel.login(email, password) { success, message ->
                if (success) {
                    val selectedId = binding.radioGroup.checkedRadioButtonId

                    val intent = when (selectedId) {
                        R.id.UserRadio -> Intent(this@LoginActivity, DashboardActivity::class.java)
                        R.id.AdminRadio -> Intent(this@LoginActivity, AdminActivity::class.java)
                        else -> {
                            Toast.makeText(this@LoginActivity, "Please select User or Admin", Toast.LENGTH_LONG).show()
                            return@login
                        }
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
