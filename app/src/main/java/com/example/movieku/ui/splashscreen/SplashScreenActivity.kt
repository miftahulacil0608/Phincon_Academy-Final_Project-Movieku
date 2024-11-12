package com.example.movieku.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movieku.R
import com.example.movieku.databinding.ActivitySplashScreenBinding
import com.example.movieku.ui.authentication.AuthenticationActivity
import com.example.movieku.ui.dashboard.MainFeaturesActivity
import com.example.movieku.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
//TODO system bar text color to white please
class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    private val splashScreenViewModel by viewModels<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //TODO logic in here
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)

            splashScreenViewModel.isUserAuthentication.observe(this@SplashScreenActivity){
                if (!it.isOnboarding){
                    startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java))
                    finish()
                }
                else if (!it.isUserAuthentication){
                    startActivity(Intent(this@SplashScreenActivity, AuthenticationActivity::class.java))
                    finish()
                }
                else{
                    startActivity(Intent(this@SplashScreenActivity, MainFeaturesActivity::class.java))
                    finish()
                }

            }

        }
    }
}