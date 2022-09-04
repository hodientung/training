package com.example.voicelockscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.voicelockscreen.view.VoiceLockFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        supportFragmentManager.beginTransaction().add(R.id.content_frame, VoiceLockFragment())
            .addToBackStack(null)
            .commit()
    }
    private fun setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setFlags(1024, 1024)

    }
}