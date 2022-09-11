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
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themePinButton
import com.example.voicelockscreen.utils.Util
import com.itsxtt.patternlock.PatternLockView
import kotlinx.android.synthetic.main.fragment_pattern_lock.*
import kotlinx.android.synthetic.main.fragment_pin_code.*


class PatternLockFragment : Fragment() {


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
        patternView.setOnPatternListener(object : PatternLockView.OnPatternListener {
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
                prefs?.patternPassword = valuePatternPassword
                activity?.supportFragmentManager?.popBackStack()
                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                    ?.replace(R.id.content_frame, ConfirmPatternFragment())?.commit()
                //tvSetPatternCode.text = getString(R.string.confirm_password)


                return true
            }
        })
    }
}