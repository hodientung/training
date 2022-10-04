package com.example.voicelockscreen.view

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isFistOnboard
import com.example.voicelockscreen.utils.Util
import kotlinx.coroutines.*

class SplashVoiceLockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(this)
        setContentView(R.layout.activity_splash_screen)

        val prefs =
            PreferenceHelper.customPreference(
                this,
                Util.ON_BOARDING
            )
        if (prefs.isFistOnboard)
            CoroutineScope(Dispatchers.Main).launch {
                delay(3000L)
                startActivity(Intent(this@SplashVoiceLockActivity, MainActivity::class.java))
                finish()
            }
        else
            CoroutineScope(Dispatchers.Main).launch {
                delay(3000L)
                startActivity(Intent(this@SplashVoiceLockActivity, OnboardActivity::class.java))
                finish()
            }

    }

    private fun getStatusBarHeight(activity: Activity): Int {
        val rec = Rect()
        val window = activity.window
        window.decorView.getWindowVisibleDisplayFrame(rec)
        return rec.top
    }

    private fun hideStatusBar(activity: Activity) {
        if (getStatusBarHeight(activity) != 0)
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        else activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}