package com.example.aura_app.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.aura_app.R
import com.example.aura_app.databinding.ActivityDashboardBinding
import com.example.aura_app.ui.fragment.CalenderFragment
import com.example.aura_app.ui.fragment.HomeFragment
import com.example.aura_app.ui.fragment.ItineryFragment
import com.example.aura_app.ui.fragment.ProfileFragment

class DashboardActivity : AppCompatActivity(){
    lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> replaceFragment(HomeFragment())
                R.id.navCalender ->replaceFragment(CalenderFragment())
                R.id.navItinery->replaceFragment(ItineryFragment())
                R.id.navProfile ->replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager =
            supportFragmentManager
        val fragmentTransaction: FragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            binding.FrameButton.id,
            fragment
        )
        fragmentTransaction.commit()
    }
}
