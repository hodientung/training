package com.example.voicelockscreen.service

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.voicelockscreen.MyApplication
import com.example.voicelockscreen.R
import com.example.voicelockscreen.view.VoiceUnlockActivity

class VoiceLockService : Service() {

    private val stateOfPhone = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when (p1?.action) {
                Intent.ACTION_SCREEN_ON -> {
                    val intent = Intent(p0, VoiceUnlockActivity::class.java)
                    intent.flags = FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                Intent.ACTION_SCREEN_OFF -> {

                }
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendNotification()

        val textLock = intent?.getStringExtra("input")
        val textUnlock = intent?.getStringExtra("inputUnlock")


        val intentToActivity = Intent("destroy_activity")
        val bundle = Bundle()
        bundle.putString("wordLock", textLock)
        bundle.putString("wordUnLock", textUnlock)
        intentToActivity.putExtras(bundle)
        sendBroadcast(intentToActivity)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(stateOfPhone, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(stateOfPhone)
    }

    private fun sendNotification() {

        val notification: Notification = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setSmallIcon(R.drawable.icon_voice_mix)
            .build()
        startForeground(1, notification)
    }


    override fun onBind(p0: Intent?): IBinder? = null
}