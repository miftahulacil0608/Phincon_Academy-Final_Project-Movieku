package com.example.movieku.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieku.R
import com.example.movieku.databinding.ActivityOnBoardingBinding
import com.example.movieku.ui.authentication.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityOnBoardingBinding.inflate(layoutInflater)
    }
    private val onBoardingViewModel by viewModels<OnBoardingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLetsGo.setOnClickListener {
            onBoardingViewModel.saveOnBoarding(true)
            startActivity(Intent(this@OnBoardingActivity, AuthenticationActivity::class.java))
            finish()
        }
    }
}