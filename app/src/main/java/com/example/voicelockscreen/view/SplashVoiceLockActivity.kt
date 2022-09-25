package com.example.voicelockscreen.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isFistOnboard
import com.example.voicelockscreen.utils.Util
import kotlinx.coroutines.*

class SplashVoiceLockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val prefs =
            PreferenceHelper.customPreference(
                this,
                Util.ON_BOARDING
            )
        if (prefs.isFistOnboard)
            CoroutineScope(Dispatchers.Main).launch {
                delay(1200L)
                startActivity(Intent(this@SplashVoiceLockActivity, MainActivity::class.java))
                finish()
            }
        else
            CoroutineScope(Dispatchers.Main).launch {
                delay(1200L)
                startActivity(Intent(this@SplashVoiceLockActivity, OnboardActivity::class.java))
                finish()
            }

    }
}