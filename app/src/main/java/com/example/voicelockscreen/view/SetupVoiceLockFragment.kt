package com.example.voicelockscreen.view

import android.animation.ObjectAnimator
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.input
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupVoiceLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import com.skyfishjy.library.RippleBackground
import kotlinx.android.synthetic.main.fragment_setup_voice_lock.*
import kotlinx.android.synthetic.main.fragment_validate_voice_lock_change.*


class SetupVoiceLockFragment : Fragment() {

    private lateinit var objectAnimator: ObjectAnimator
    private var rippleBackground: RippleBackground? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup_voice_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        rippleBackground = RippleBackground(context)
        rippleBackground = content1
        startAnimationImage()
        imKara.setOnClickListener {
            startAnimationRipple()
            tvDescription.text = getString(R.string.please_speak_something)
            startListeningRecognitionService()
        }
        imBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun startAnimationImage() =
        Handler(Looper.getMainLooper()).postDelayed({
            objectAnimator =
                ObjectAnimator.ofFloat(animation_image, View.ROTATION, 0.0f, 360.0f)
            objectAnimator.repeatCount = Animation.INFINITE
            objectAnimator.interpolator = LinearInterpolator()
            objectAnimator.duration = 2000
            objectAnimator.start()
        }, 100)


    private fun cancelAnimationImage() =
        Handler(Looper.getMainLooper()).postDelayed({ objectAnimator.cancel() }, 100)

    private fun startAnimationRipple() = Handler(Looper.getMainLooper()).postDelayed({
        rippleBackground?.startRippleAnimation()
    }, 100)

    private fun cancelAnimationRipple() =
        Handler(Looper.getMainLooper()).postDelayed(
            { rippleBackground?.stopRippleAnimation() },
            100
        )


    override fun onPause() {
        super.onPause()
        Log.e("tung", "onPause1")
        cancelAnimationImage()

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
                val errorMessage: String? = Util.getErrorText(p0, context)
                if (errorMessage?.isNotBlank() == true){
                    tvDescription.text = errorMessage
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            override fun onResults(p0: Bundle?) {
                Log.e("tung", "onResult")
                cancelAnimationRipple()
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
                        tvDescription.text = getString(R.string.create_n_new_voice_password)
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

}