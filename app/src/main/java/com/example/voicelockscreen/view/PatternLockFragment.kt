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
import com.example.voicelockscreen.sharepreference.PreferenceHelper.patternPassword
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_pattern_lock.*


class PatternLockFragment : Fragment() {
    private var isSetupPassword = false

    private fun setTheme() {
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.THEME_SETTING
                )
            }
        val listTheme = prefs?.themeCode?.let {
            Util.getThemeToScreen(it)
        }
        if (prefs?.themeCode != -1)
            Util.setThemePatternView(
                content1,
                tvDescriptionPattern,
                tvBackPatternLock,
                imBackgroundVoicePattern,
                imVPattern,
                listTheme,
                requireContext(),
                patternView
            )
        else Util.setOriginalPatternScreen(
            imBackgroundVoicePattern,
            tvDescriptionPattern,
            patternView
        )

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
        setTheme()
        tvDescriptionPattern.text = getString(R.string.draw_new_your_pattern)
        Toast.makeText(
            context, getString(R.string.draw_new_your_pattern),
            Toast.LENGTH_LONG
        ).show()
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
        tvBackPatternLock.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}