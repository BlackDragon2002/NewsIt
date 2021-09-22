package com.example.newsit.ui

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.postDelayed
import com.example.newsit.R
import com.example.newsit.databinding.ActivitySplashScreenBinding
import com.example.newsit.util.Constants.Companion.SAWO_API_KEY
import com.example.newsit.util.Constants.Companion.SAWO_SECRET_KEY
import com.sawolabs.androidsdk.Sawo

class SplashScreen : AppCompatActivity() {
    lateinit var binding :ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pgSplash.postDelayed(1000){
            binding.btnLogin.visibility=View.VISIBLE
            binding.pgSplash.visibility=View.INVISIBLE
        }
        binding.btnLogin.setOnClickListener {
            Sawo(
                this,
                SAWO_API_KEY, // your api key
                SAWO_SECRET_KEY  // your api key secret
            ).login(
                "email", // can be one of 'email' or 'phone_number_sms'
                NewsActivity::class.java.name // Callback class name
            )
        }

    }
}