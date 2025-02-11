package com.pricematch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.pricematch.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.splashText.startAnimation(fadeInAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            checkUserState()
        }, 3000)
    }

    private fun checkUserState() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, redirect to DashboardActivity
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is not signed in, redirect to SingInActivity
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}