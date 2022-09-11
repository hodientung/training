package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.patternPassword
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import com.itsxtt.patternlock.PatternLockView
import kotlinx.android.synthetic.main.fragment_confirm_pattern.*
import kotlinx.android.synthetic.main.fragment_validate_pattern_lock_change.*


class ValidatePatternLockChangeFragment : Fragment() {


    override fun onResume() {
        super.onResume()
        setTheme()
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
            ?.let { content3.setBackgroundResource(it) }
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
        patternViewValidate.setOnPatternListener(object : PatternLockView.OnPatternListener {
            override fun onStarted() {
                super.onStarted()
            }

            override fun onProgress(ids: ArrayList<Int>) {
                super.onProgress(ids)
            }

            override fun onComplete(ids: ArrayList<Int>): Boolean {
                /*
                 * A return value required
                 * if the pattern is not correct and you'd like change the pattern to error state, return false
                 * otherwise return true
                 */
                var valuePatternPassword = ""
                for (value in ids) {
                    valuePatternPassword += value.toString()
                }
                val prefs = context?.let {
                    PreferenceHelper.customPreference(
                        it,
                        Util.PATTERN_INPUT
                    )
                }
                if (prefs?.patternPassword == valuePatternPassword) {
                    activity?.supportFragmentManager?.popBackStack()
                    activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                        ?.replace(R.id.content_frame, PatternCodeEstablishFragment())?.commit()
                } else
                    Toast.makeText(
                        context, getString(R.string.wrong_password),
                        Toast.LENGTH_LONG
                    ).show()
                return true
            }
        })
    }

}