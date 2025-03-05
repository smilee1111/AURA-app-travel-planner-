package com.example.aura_app.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.aura_app.R

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (checkUserLoginStatus()) {
                Intent(this, DashboardActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 1500)
    }

    private fun checkUserLoginStatus(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }
}