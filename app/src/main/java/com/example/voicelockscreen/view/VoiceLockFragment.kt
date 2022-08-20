package com.example.voicelockscreen.view

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.voicelockscreen.R
import com.example.voicelockscreen.service.VoiceLockService
import com.example.voicelockscreen.sharepreference.PreferenceHelper.customPreference
import com.example.voicelockscreen.sharepreference.PreferenceHelper.input
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_voice_lock.*
import java.util.*


class VoiceLockFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.fragment_voice_lock,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSpeak.setOnClickListener {
            promptSpeechInput()
        }
        btnStartService.setOnClickListener {
            checkOverlayPermission()
            startService()
        }
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(context)) {
            val intent = Intent(context, VoiceLockService::class.java)
            context?.startService(intent)
        }
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(context)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
        }
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                context,
                getString(R.string.speech_not_supported),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && data != null) {
                    val result: ArrayList<String> =
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                    val prefs = context?.let { customPreference(it, Util.CUSTOM_PREF_NAME) }
                    prefs?.input = result[0]

                }
            }
            else -> {}
        }
    }

    companion object {
        const val REQ_CODE_SPEECH_INPUT = 100
    }
}