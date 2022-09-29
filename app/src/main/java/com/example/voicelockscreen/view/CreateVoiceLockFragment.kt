package com.example.voicelockscreen.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.input
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupVoiceLock
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import com.skyfishjy.library.RippleBackground
import kotlinx.android.synthetic.main.fragment_create_voice_lock.*

class CreateVoiceLockFragment : Fragment() {

    private lateinit var objectAnimator: ObjectAnimator
    private lateinit var rippleBackground: RippleBackground

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_voice_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        startAnimationImage()
        rippleBackground = RippleBackground(requireContext())
        rippleBackground = content1Create
        imKaraCreate.setOnClickListener {
            startAnimationRipple()
            tvDescriptionCreate.text = getString(R.string.please_speak_something)
            startListeningRecognitionService()
        }
        imBackCreate.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
            AlternativeLockFragment().pushToScreen(activity as MainActivity)
        }
    }

    private fun startAnimationImage() =
        Handler(Looper.getMainLooper()).postDelayed({
            objectAnimator =
                ObjectAnimator.ofFloat(animation_image_create, View.ROTATION, 0.0f, 360.0f)
            objectAnimator.repeatCount = Animation.INFINITE
            objectAnimator.interpolator = LinearInterpolator()
            objectAnimator.duration = 2000
            objectAnimator.start()
        }, 100)


    private fun cancelAnimationImage() =
        Handler(Looper.getMainLooper()).postDelayed({ objectAnimator.cancel() }, 100)

    private fun startAnimationRipple() = Handler(Looper.getMainLooper()).postDelayed({
        if (!rippleBackground.isRippleAnimationRunning)
            rippleBackground.startRippleAnimation()
    }, 100)

    private fun cancelAnimationRipple() =
        Handler(Looper.getMainLooper()).postDelayed(
            { if (rippleBackground.isRippleAnimationRunning) rippleBackground.stopRippleAnimation() },
            100
        )


    override fun onPause() {
        super.onPause()
        Log.e("tung", "onPause1")
        cancelAnimationImage()
        //cancelAnimationRipple()

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
                cancelAnimationRipple()
                val errorMessage: String = getErrorText(p0)
                tvDescriptionCreate.text = errorMessage
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }

            override fun onResults(p0: Bundle?) {
                Log.e("tung", "onResult")
                cancelAnimationRipple()
                val voiceResults = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (voiceResults == null) tvDescriptionCreate.text =
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
                        tvDescriptionCreate.text = getString(R.string.create_n_new_voice_password)
                        prefs?.input = result
                        prefs?.isSetupVoiceLock = true
                        val importantDialogFragment = ImportantDialogFragment()
                        importantDialogFragment.onClose = {
                            activity?.supportFragmentManager?.popBackStack()
                        }
                        activity?.supportFragmentManager?.let {
                            importantDialogFragment.show(
                                it,
                                Util.TAG
                            )
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