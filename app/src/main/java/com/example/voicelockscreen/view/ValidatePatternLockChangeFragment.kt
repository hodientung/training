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
import kotlinx.android.synthetic.main.fragment_validate_pattern_lock_change.*


class ValidatePatternLockChangeFragment : Fragment() {


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
                tvDescriptionPatternValidate,
                tvBackPatternLockValidate,
                imBackgroundVoicePatternValidate,
                imVPatternValidate,
                listTheme,
                requireContext(),
                patternViewValidate
            )
        else Util.setOriginalPatternScreen(
            imBackgroundVoicePatternValidate,
            tvDescriptionPatternValidate,
            patternViewValidate,
            requireContext()
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_validate_pattern_lock_change, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTheme()
        initAction()
    }

    private fun initAction() {
        val prefs = context?.let {
            PreferenceHelper.customPreference(
                it,
                Util.PATTERN_INPUT
            )
        }
        Toast.makeText(
            requireContext(),
            getString(R.string.draw_old_your_pattern),
            Toast.LENGTH_LONG
        )
            .show()
        tvDescriptionPatternValidate.text = getString(R.string.draw_old_your_pattern)
        patternViewValidate.onCheckPattern = { it ->

            val pattern = prefs?.patternPassword
            if (it == pattern) {
                activity?.supportFragmentManager?.popBackStack()
                PatternCodeEstablishFragment().pushToScreen(activity as MainActivity)
            } else {
                Toast.makeText(
                    context, getString(R.string.wrong_pattern_code),
                    Toast.LENGTH_LONG
                ).show()
                tvDescriptionPatternValidate.text = getString(R.string.wrong_pattern_code)
            }
        }
        tvBackPatternLockValidate.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}