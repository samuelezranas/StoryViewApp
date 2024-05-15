package com.dicoding.storyviewapp.ui.main.start

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyviewapp.R
import com.dicoding.storyviewapp.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000L)
    }
}