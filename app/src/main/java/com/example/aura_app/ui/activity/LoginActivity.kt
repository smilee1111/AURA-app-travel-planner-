package com.example.aura_app.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aura_app.R
import com.example.aura_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        binding.LogIn.setOnClickListener {
            val username: String = binding.user.text.toString()
            val password: String = binding.Pass123.text.toString()

            if (username.isEmpty()) {
                binding.user.error = "Please enter your Username"
            } else if (password.isEmpty()) {
                binding.Pass123.error = "Please enter your Password"
            } else {
                if (binding.checkBox.isChecked) {
                    val editor = sharedPreferences.edit()
                    editor.putString("username", username)
                    editor.putString("password", password)
                    editor.apply()
                }
            }
            setContentView(R.layout.activity_login)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}