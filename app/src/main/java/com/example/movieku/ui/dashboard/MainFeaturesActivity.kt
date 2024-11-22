package com.example.movieku.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieku.R
import com.example.movieku.databinding.ActivityMainFeaturesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFeaturesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainFeaturesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainFeaturesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main_features)

        navController.addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when (destination.id) {
                    R.id.navigation_home -> {
                        binding.navView.visibility = View.VISIBLE
                    }

                    R.id.navigation_ticket -> {
                        binding.navView.visibility = View.VISIBLE
                    }

                    R.id.navigation_watchlist -> {
                        binding.navView.visibility = View.VISIBLE
                    }

                    R.id.navigation_profile -> {
                        binding.navView.visibility = View.VISIBLE
                    }

                    else -> {
                        binding.navView.visibility = View.GONE
                    }
                }
            }

        })
        navView.setupWithNavController(navController)
    }
}