package com.example.voicelockscreen.service

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.example.voicelockscreen.MyApplication
import com.example.voicelockscreen.R
import com.example.voicelockscreen.view.LockViewScreen
import com.example.voicelockscreen.view.VoiceUnlockActivity

class VoiceLockService : Service() {
    lateinit var windowManager: WindowManager
    lateinit var view: View

    private val stateOfPhone = object : BroadcastReceiver() {
        private var screenOn = false
        override fun onReceive(p0: Context?, p1: Intent?) {

            when (p1?.action) {
                ACTION_SCREEN_ON -> {
                    Log.e("tung", "screen on")
                    screenOn = true;
                    onLockScreen()


                }
                ACTION_SCREEN_OFF -> {
                    Log.e("tung", "screen off")
                    screenOn = false;
                    offLockScreen()
                }
            }
        }
    }

    fun getLayoutParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            0,
            0,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
            PixelFormat.TRANSPARENT
        )
    }

    fun onLockScreen() {
        windowManager.addView(view, getLayoutParams())
    }

    fun offLockScreen() {
        windowManager.removeView(view)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendNotification()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        view = LayoutInflater.from(this).inflate(R.layout.fragment_voice_unlock, null)
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