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
import com.example.aura_app.ui.fragment.HomeFragment
import com.example.aura_app.ui.fragment.ItineryFragment
import com.example.aura_app.ui.fragment.ProfileFragment

class DashboardActivity : AppCompatActivity(), DashboardAdapter.OnItemClickListener {

    lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageList = arrayListOf(R.drawable.ktm, R.drawable.boudha, R.drawable.himal)
        val nameList = arrayListOf("Nepal, Kathmandu", "Switzerland, Bern", "Other Destination")
        val descList = arrayListOf(
            "Nepal, it is mainly situated in the Himalayas...",
            "Home to numerous lakes, villages...",
            "Description of another destination"
        )
        val priceList = arrayListOf("Starting from $500", "Starting from $1000", "Starting from $750")

        val recyclerView: RecyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DashboardAdapter(this, imageList, nameList, descList, priceList, this)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> replaceFragment(HomeFragment())
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
            replaceFragment(HomeFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.FrameButton.id, fragment)
            .addToBackStack(null)
            .commit()
    }
}
