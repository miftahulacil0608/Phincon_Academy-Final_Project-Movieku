package com.example.movieku.ui.dashboard

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieku.R
import com.example.movieku.databinding.ActivityMainFeaturesBinding
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

        navView.setupWithNavController(navController)

    }
}