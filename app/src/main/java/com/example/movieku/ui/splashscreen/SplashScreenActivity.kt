package com.example.movieku.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.movieku.R
import com.example.movieku.databinding.ActivitySplashScreenBinding
import com.example.movieku.ui.onboarding.OnBoardingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
//TODO system bar text color to white please
class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //TODO logic in here
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)

            //jika dataa auth user ga ada maka minta login ulang
            startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java))
            finish()
            //jika data ada maka masuk ke halaman main features

        }
    }
}