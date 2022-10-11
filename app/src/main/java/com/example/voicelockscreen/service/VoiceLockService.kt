package com.example.voicelockscreen.service

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.example.voicelockscreen.MyApplication
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.codeLanguage
import com.example.voicelockscreen.sharepreference.PreferenceHelper.input
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.view.*
import java.util.*


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
                    windowSecurityQuestion.onBackButton()
                    windowPinLock.onBackButton()
                    windowPatternLock.onBackButton()
                    windowTimerPin.onBackButton()

                    window.getView()?.findViewById<ImageView>(R.id.imBackgroundVoiceLock)
                        ?.setOnClickListener {
                            val tvText = window.getView()?.findViewById<TextView>(R.id.tvTitle)
                            tvText?.text = getString(R.string.speak_password_to_unlock)
                            window.startAnimationRipple()
                            startListeningRecognitionService()
                        }
                    window.getView()?.findViewById<TextView>(R.id.tvForgetLock)
                        ?.setOnClickListener {
                            windowSecurityQuestion.open()
                        }

                    window.getView()?.findViewById<ImageView>(R.id.view1WinLock)
                        ?.setOnClickListener {
                            // open verify pin lock
                            windowPinLock.open()

                        }
                    window.getView()?.findViewById<ImageView>(R.id.view2WinLock)
                        ?.setOnClickListener {
                            // open verify pattern lock
                            windowPatternLock.open()
                        }
                    window.getView()?.findViewById<ImageView>(R.id.view3WinLock)
                        ?.setOnClickListener {
                            // open verify timer pin
                            windowTimerPin.open()

                        }
                }
                ACTION_SCREEN_OFF -> {
                    Log.e("tung", "screen off")
                    window.open()
                }
            }
        }
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
                window.cancelAnimationRipple()
                val errorMessage: String? = window.context?.let { Util.getErrorText(p0, it) }
                val tvText = window.getView()?.findViewById<TextView>(R.id.tvTitle)
                if (errorMessage?.isNotBlank() == true)
                    tvText?.text = errorMessage
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
                        window.cancelAnimationRipple()
                        if (match.toString() == firstInput) {
                            window.close()
                        } else {
                            val tvText = window.getView()?.findViewById<TextView>(R.id.tvTitle)
                            tvText?.text = getString(R.string.sorry_password_voice)
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
        return START_REDELIVER_INTENT
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
        val prefs = this.let {
            this.let { it1 ->
                PreferenceHelper.customPreference(
                    it1,
                    Util.DATA_LANGUAGE_APP
                )
            }
        }
        val config = this.resources?.configuration
        prefs.codeLanguage?.let {
            val locale = Locale(it)
            config?.setLocale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                config?.let { it1 -> this.createConfigurationContext(it1) }
            this.resources?.updateConfiguration(config, this.resources?.displayMetrics)
        }
        val notification: Notification =
            NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle(getText(R.string.voice_lock_screen))
                .setSmallIcon(R.drawable.icon_voice_mix)
                .build()
        startForeground(1, notification)
    }


    override fun onBind(p0: Intent?): IBinder? = null
}