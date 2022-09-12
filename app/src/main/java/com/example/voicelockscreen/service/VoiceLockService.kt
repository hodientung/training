package com.example.voicelockscreen.service

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import com.example.voicelockscreen.MyApplication
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.input
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.view.*


class VoiceLockService : Service() {

    lateinit var window: Window
    lateinit var windowSecurityQuestion: WindowSecurityQuestion
    lateinit var windowPinLock: WindowPinLock
    lateinit var windowPatternLock: WindowPatternLock
    lateinit var windowTimerPin: WindowTimerPin
    private val stateOfPhone = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            when (p1?.action) {
                ACTION_SCREEN_ON -> {
                    Log.e("tung", "screen on")
                    window.open()
                    window.getView()?.findViewById<CardView>(R.id.btnSpeakUnlock1)
                        ?.setOnClickListener {
                            startListeningRecognitionService()
                        }
                    setTheme(
                        p0,
                        window.getView()?.findViewById<ConstraintLayout>(R.id.content_add_view)
                    )
                    window.getView()?.findViewById<AppCompatButton>(R.id.btnForgetPassword)
                        ?.setOnClickListener {
                            windowSecurityQuestion.open()
                        }

                    window.getView()?.findViewById<CardView>(R.id.cardViewPin)
                        ?.setOnClickListener {
                            // open verify pin lock
                            windowPinLock.open()

                        }
                    window.getView()?.findViewById<CardView>(R.id.cardViewPin3)
                        ?.setOnClickListener {
                            // open verify pattern lock
                            windowPatternLock.open()
                        }
                    window.getView()?.findViewById<CardView>(R.id.cardViewPin2)
                        ?.setOnClickListener {
                            // open verify timer pin
                            windowTimerPin.open()

                        }
                }
                ACTION_SCREEN_OFF -> {
                    Log.e("tung", "screen off")
                }
            }
        }
    }

    private fun setTheme(context: Context?, view: View?) {
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.THEME_SETTING
                )
            }
        prefs?.themeCode?.let { Util.getThemeToScreen(it).colorTheme }
            ?.let { view?.setBackgroundResource(it) }

    }


    private fun startListeningRecognitionService() {

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_CALLING_PACKAGE,
            packageName
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )

        val recognition = SpeechRecognizer.createSpeechRecognizer(this)
        val recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                Log.e("tung", "onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {
                Log.e("tung", "onBeginningOfSpeech")
            }

            override fun onRmsChanged(p0: Float) {
                Log.e("tung", "onRmsChanged")
            }

            override fun onBufferReceived(p0: ByteArray?) {
                Log.e("tung", "onBufferReceived")
            }

            override fun onEndOfSpeech() {
                Log.e("tung", "onEndOfSpeech")
            }

            override fun onError(p0: Int) {
                Log.e("tung", "Error listening for speech: $p0")
            }

            override fun onResults(p0: Bundle?) {
                Log.e("tung", "onResult")
                val prefs =
                    PreferenceHelper.customPreference(
                        applicationContext,
                        Util.CUSTOM_PREF_NAME
                    )
                val firstInput = prefs.input
                val voiceResults = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (voiceResults == null) {
                    println("No voice results")
                } else {
                    for (match in voiceResults) {
                        window.getView()?.findViewById<TextView>(R.id.test_thu)?.text =
                            match.toString()
                        if (match.toString() == firstInput) {
                            window.close()
                        }
                    }
                }
            }

            override fun onPartialResults(p0: Bundle?) {
                //TODO("Not yet implemented")
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
                //TODO("Not yet implemented")
            }

        }
        recognition.setRecognitionListener(recognitionListener)
        recognition.startListening(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendNotification()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        window = Window(this)
        windowSecurityQuestion = WindowSecurityQuestion(this) {
            window.close()
        }
        windowPinLock = WindowPinLock(this) {
            window.close()
        }
        windowPatternLock = WindowPatternLock(this) {
            window.close()
        }
        windowTimerPin = WindowTimerPin(this) {
            window.close()
        }
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

        val notification: Notification =
            NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle(getText(R.string.notification_title))
                .setSmallIcon(R.drawable.icon_voice_mix)
                .build()
        startForeground(1, notification)
    }


    override fun onBind(p0: Intent?): IBinder? = null
}