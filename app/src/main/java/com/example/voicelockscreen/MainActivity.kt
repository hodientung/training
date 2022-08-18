package com.example.voicelockscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.voicelockscreen.view.VoiceLockFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, VoiceLockFragment())
            .addToBackStack(null)
            .commit()
    }
}