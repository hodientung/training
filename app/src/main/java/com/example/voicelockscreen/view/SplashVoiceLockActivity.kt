package com.example.voicelockscreen.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import kotlinx.coroutines.*

class SplashVoiceLockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1200L)
            startActivity(Intent(this@SplashVoiceLockActivity, OnboardActivity::class.java))
            finish()
        }
    }
}