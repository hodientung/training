package com.example.voicelockscreen.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.voicelockscreen.R

class VoiceUnlockActivity : AppCompatActivity() {

    private val statusScreenBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val bundle = p1?.extras ?: return
            val text1 = bundle.getString("wordLock")
            val text2 = bundle.getString("wordUnLock")
            if (text1 == text2) Toast.makeText(p0, "dung mat khau roi", Toast.LENGTH_LONG).show()
            else Toast.makeText(p0, "khong hop le cho lam", Toast.LENGTH_LONG).show()

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_unlock)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_unlock, VoiceUnlockFragment()).commit()
        registerReceiver(statusScreenBroadcastReceiver, IntentFilter("destroy_activity"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(statusScreenBroadcastReceiver)
    }
}