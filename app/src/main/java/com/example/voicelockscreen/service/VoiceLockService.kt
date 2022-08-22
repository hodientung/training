package com.example.voicelockscreen.service

import android.app.Dialog
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
import android.widget.ImageButton
import android.widget.TextView
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
                    window.getView()?.findViewById<ImageButton>(R.id.btnSpeakUnlock)
                        ?.setOnClickListener {
                           //
                        }
                }
                ACTION_SCREEN_OFF -> {
                    Log.e("tung", "screen off")
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

        val formattedSpeech = StringBuffer()
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
                val voiceResults = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (voiceResults == null) {
                    println("No voice results")
                } else {
                    for (match in voiceResults) {
                        formattedSpeech.append(String.format("\n- %s", match.toString()))
                        window.getView()?.findViewById<TextView>(R.id.test_thu)?.text =
                            formattedSpeech.toString()
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