package com.example.aura_app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aura_app.R
import com.example.aura_app.databinding.ActivityLoginBinding
import com.example.aura_app.repository.UserRepositoryImpl
import com.example.aura_app.viewModel.UserViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var repo =UserRepositoryImpl()
        userViewModel = UserViewModel(repo)
        // Handle Login Button Click
        binding.LoginBtn.setOnClickListener{
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            userViewModel.login(email,password){
                    success,message->
                if(success){
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                    val intent = Intent (this@LoginActivity,DashboardActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@LoginActivity,
                        message, Toast.LENGTH_LONG).show()
                }
            }
        }

        // Handle Sign-Up Button Click
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        // Handle System Window Insets for UI Adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
