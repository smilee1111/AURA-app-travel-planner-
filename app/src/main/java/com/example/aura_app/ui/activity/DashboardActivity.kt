package com.example.aura_app.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.adapter.DashboardAdapter
import com.example.aura_app.databinding.ActivityDashboardBinding
import com.example.aura_app.ui.fragment.CalenderFragment
import com.example.aura_app.ui.fragment.ExploreFragment
import com.example.aura_app.ui.fragment.HomeFragment
import com.example.aura_app.ui.fragment.ItineryFragment
import com.example.aura_app.ui.fragment.ProfileFragment

class DashboardActivity : AppCompatActivity(), DashboardAdapter.OnItemClickListener {
    lateinit var binding:ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {menuItem->
            when (menuItem.itemId) {
                R.id.navHome -> replaceFragment(HomeFragment())
                R.id.navExplore -> replaceFragment(ExploreFragment())
                R.id.navCalender -> replaceFragment(CalenderFragment())
                R.id.navItinery -> replaceFragment(ItineryFragment())
                R.id.navProfile -> replaceFragment(ProfileFragment())
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

    override fun onImageClick(position: Int) {
        if (position == 0) { // KTM image is clicked
            replaceFragment(ExploreFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.FrameButton)
        if (currentFragment != null && currentFragment::class == fragment::class) {
            // Fragment is already displayed, no need to replace
            return
        }

        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.FrameButton, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
