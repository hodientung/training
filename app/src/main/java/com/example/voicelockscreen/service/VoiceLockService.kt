package com.example.voicelockscreen.service

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.voicelockscreen.MyApplication
import com.example.voicelockscreen.R
import com.example.voicelockscreen.view.Window

class VoiceLockService : Service() {

    lateinit var window: Window

    private val stateOfPhone = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            when (p1?.action) {
                ACTION_SCREEN_ON -> {
                    Log.e("tung", "screen on")
                    window.open()
                }
                ACTION_SCREEN_OFF -> {
                    Log.e("tung", "screen off")
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendNotification()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        window = Window(this)
        val filter = IntentFilter()
        filter.addAction(ACTION_SCREEN_ON)
        filter.addAction(ACTION_SCREEN_OFF)
        filter.addAction(ACTION_USER_PRESENT)
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