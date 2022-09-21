package com.example.voicelockscreen.view

import android.content.*
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.input
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_setup_voice_lock.*
import kotlinx.android.synthetic.main.fragment_validate_voice_lock_change.*

class ValidateVoiceLockChangeFragment : Fragment() {


    override fun onResume() {
        super.onResume()
        tvDescriptionValidate.text = getString(R.string.tap_on_mic_n_conf)
        //setTheme()
    }

    //show theme for layout
    private fun setTheme() {
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.THEME_SETTING
                )
            }

//        prefs?.themeCode?.let { Util.getThemeToScreen(it).colorTheme }
//            ?.let { contentValidateVoiceLockValidate.setBackgroundResource(it) }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_validate_voice_lock_change, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        imKaraValidate.setOnClickListener {
            tvDescriptionValidate.text = getString(R.string.please_speak_something)
            startListeningRecognitionService()
        }
        imBackValidate.setOnClickListener {
            AlternativeLockFragment().pushToScreen(activity as MainActivity)
        }
    }

    private fun startListeningRecognitionService() {

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )

        val recognition = SpeechRecognizer.createSpeechRecognizer(requireContext())
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
                val errorMessage: String = getErrorText(p0)
                tvDescriptionValidate.text = errorMessage
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }

            override fun onResults(p0: Bundle?) {
                Log.e("tung", "onResult")
                val voiceResults = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (voiceResults == null) tvDescription.text =
                    getString(R.string.did_not_understand)
                else {
                    for (match in voiceResults) {
                        val result = match.trimIndent()
                        val prefs = context?.let {
                            PreferenceHelper.customPreference(
                                it,
                                Util.CUSTOM_PREF_NAME
                            )
                        }
                        if (prefs?.input == result) {
                            Toast.makeText(
                                context,
                                getString(R.string.correct_password),
                                Toast.LENGTH_LONG
                            ).show()
                            activity?.supportFragmentManager?.popBackStack()
                            SetupVoiceLockFragment().pushToScreen(activity as MainActivity)
                        } else {
                            tvDescriptionValidate.text = getString(R.string.in_correct_password)
                            Toast.makeText(
                                context,
                                getString(R.string.sorry_password_voice),
                                Toast.LENGTH_LONG
                            ).show()
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

    private fun getErrorText(error: Int): String {
        val message: String = when (error) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error, please try again."
            SpeechRecognizer.ERROR_CLIENT -> "Client side error, please try again."
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions, please try again."
            SpeechRecognizer.ERROR_NETWORK -> "Network error, please try again."
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout, please try again."
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy, please try again."
            SpeechRecognizer.ERROR_SERVER -> "error from server, please try again."
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input, please try again."
            else -> "Didn't understand, please try again."
        }
        return message
    }
}