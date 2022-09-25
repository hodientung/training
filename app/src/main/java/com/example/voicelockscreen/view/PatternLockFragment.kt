package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupPatternLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.patternPassword
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import com.itsxtt.patternlock.PatternLockView
import kotlinx.android.synthetic.main.fragment_pattern_lock.*
import kotlinx.android.synthetic.main.fragment_pin_code.*


class PatternLockFragment : Fragment() {
    private var isSetupPassword = false

    override fun onResume() {
        super.onResume()
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

        prefs?.themeCode?.let { Util.getThemeToScreen(it).colorTheme }
            ?.let { content1.setBackgroundResource(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pattern_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDescriptionPattern.text = getString(R.string.draw_new_your_pattern)
        Toast.makeText(
            context, getString(R.string.draw_new_your_pattern),
            Toast.LENGTH_LONG
        ).show()
        //patternView.setImageRes(R.drawable.ic_icon_eclipse_pattern)
        patternView.setImageRes(R.drawable.ic_icon_eclipse_pattern)
        patternView.setColorPath(R.color.color_2D78F4)
        val prefs = context?.let {
            PreferenceHelper.customPreference(
                it,
                Util.PATTERN_INPUT
            )
        }
        patternView.onCheckPattern = { it ->
            prefs?.patternPassword = it
            isSetupPassword = true
            activity?.supportFragmentManager?.popBackStack()
            ConfirmPatternFragment().pushToScreen(activity as MainActivity)
        }
//        patternView.onCheckPattern = { it ->
//            if (isSetupPassword) {
//                val pattern = prefs?.patternPassword
//                if (it == pattern) {
//                    prefs.isSetupPatternLock = true
//                    Toast.makeText(
//                        context, getString(R.string.successful_set_pattern_lock),
//                        Toast.LENGTH_LONG
//                    ).show()
//                    activity?.supportFragmentManager?.popBackStack()
//                    SetupVoiceLockFragment().pushToScreen(activity as MainActivity)
//                } else {
//                    Toast.makeText(
//                        context, getString(R.string.wrong_pattern_code),
//                        Toast.LENGTH_LONG
//                    ).show()
//                    tvDescriptionPattern.text = getString(R.string.wrong_pattern_code)
//                }
//            }
//        }
        tvBackPatternLock.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}