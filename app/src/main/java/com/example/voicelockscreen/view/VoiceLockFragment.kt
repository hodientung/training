package com.example.voicelockscreen.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.service.VoiceLockService
import com.example.voicelockscreen.sharepreference.PreferenceHelper.customPreference
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetTimerPin
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupPatternLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupPinLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupVoiceLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.onService
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_voice_lock.*

class VoiceLockFragment : Fragment() {

    private var isEnableService = false
    private lateinit var adapterFunction: RecyclerViewFunction

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
        initView()
        initAction()
    }

    private fun initView() {
        rvFunction.layoutManager = GridLayoutManager(context, 3)
        adapterFunction = RecyclerViewFunction(context)
        adapterFunction.functionList = Util.getFunctionList(resources)
        rvFunction.adapter = adapterFunction
    }

    private fun initAction() {

        //Check state of switch when service is active
        val prefs = context?.let { customPreference(it, Util.CUSTOM_PREF_NAME) }
        val prefsPinCode =   //inputPinLock
            context?.let {
                customPreference(
                    it,
                    Util.PIN_LOCK_CUSTOM_PREF_NAME
                )
            }
        val prefsPatternPassword = //patternPassword
            context?.let {
                customPreference(
                    it,
                    Util.PATTERN_INPUT
                )
            }
        val prefsTimerPin = // isSetTimerPin
            context?.let {
                customPreference(
                    it,
                    Util.TIMER_PIN_PREF_NAME
                )
            }

        switchMaterial.isChecked = prefs?.onService == true
        if (switchMaterial.isChecked) startService()
        switchMaterial.setOnClickListener {
            if (prefs?.isSetupVoiceLock == false || (prefsPinCode?.isSetupPinLock == false
                        && prefsPatternPassword?.isSetupPatternLock == false
                        && prefsTimerPin?.isSetTimerPin == false)
            ) {
                switchMaterial.isChecked = false
                val toast = Toast.makeText(
                    context,
                    getString(R.string.first_set_the_voice_password),
                    Toast.LENGTH_LONG
                )
                val viewGroup = toast.view as ViewGroup?
                viewGroup?.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.E3F2FD
                    )
                )
                val textView = viewGroup?.getChildAt(0) as TextView
                textView.textSize = 13f
                textView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.size_828282
                    )
                )
                toast.show()
            } else {
                if (switchMaterial.isChecked) {
                    isEnableService = true
                    prefs?.onService = isEnableService
                    startService()
                } else {
                    isEnableService = false
                    prefs?.onService = isEnableService
                    stopService()
                }
            }
        }

        // handle event for functions of application
        adapterFunction.onItemClicked = {
            when (it) {
                0 ->
                    if (prefs?.isSetupVoiceLock == true)
                    // open fragment enter old password to open voice lock screen setup new password
                        ValidateVoiceLockChangeFragment().pushToScreen(activity as MainActivity)
                    else
                    //exchange to security question
                        SecurityQuestionFragment().pushToScreen(activity as MainActivity)

                1 ->
                    if (prefs?.isSetupVoiceLock == true)
                        ValidatePinLockChangeFragment().pushToScreen(activity as MainActivity)
                    else {
                        val toast = Toast.makeText(
                            context,
                            getString(R.string.please_install_voice_lock),
                            Toast.LENGTH_LONG
                        )
                        val viewGroup = toast.view as ViewGroup?
                        viewGroup?.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.E3F2FD
                            )
                        )
                        val textView = viewGroup?.getChildAt(0) as TextView
                        textView.textSize = 13f
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.size_828282
                            )
                        )
                        toast.show()
                    }
                2 ->
                    //to do pattern lock view
                    if (prefs?.isSetupVoiceLock == true)
                        ValidatePatternLockChangeFragment().pushToScreen(activity as MainActivity)
                    else {
                        val toast = Toast.makeText(
                            context,
                            getString(R.string.please_install_voice_lock2),
                            Toast.LENGTH_LONG
                        )
                        val viewGroup = toast.view as ViewGroup?
                        viewGroup?.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.E3F2FD
                            )
                        )
                        val textView = viewGroup?.getChildAt(0) as TextView
                        textView.textSize = 13f
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.size_828282
                            )
                        )
                        toast.show()
                    }

                3 ->
                    // refer fragment list theme
                    ThemeFragment().pushToScreen(activity as MainActivity)

                4 ->
                    //Show list folder contain image in storage
                    ImageFolderFragment().pushToScreen(activity as MainActivity)

                5 ->
                    //Show list folder contain video in storage
                    VideoFolderFragment().pushToScreen(activity as MainActivity)

                6 ->
                    SettingFragment().pushToScreen(activity as MainActivity)

            }
        }
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(context)) {
            val intent = Intent(context, VoiceLockService::class.java)
            context?.startService(intent)
        }
    }

    private fun stopService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(context)) {
            val intent = Intent(context, VoiceLockService::class.java)
            context?.stopService(intent)
        }
    }
}