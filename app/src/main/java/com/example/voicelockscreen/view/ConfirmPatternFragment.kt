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
import kotlinx.android.synthetic.main.fragment_confirm_pattern.*
import kotlinx.android.synthetic.main.fragment_pattern_lock.*


class ConfirmPatternFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        //setTheme()
    }

    //show theme for layout
//    private fun setTheme() {
//        val prefs =
//            context?.let {
//                PreferenceHelper.customPreference(
//                    it,
//                    Util.THEME_SETTING
//                )
//            }
//
//        prefs?.themeCode?.let { Util.getThemeToScreen(it).colorTheme }
//            ?.let { content2.setBackgroundResource(it) }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_pattern, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = context?.let {
            PreferenceHelper.customPreference(
                it,
                Util.PATTERN_INPUT
            )
        }
        Toast.makeText(
            context, getString(R.string.draw_pattern_again),
            Toast.LENGTH_LONG
        ).show()
        tvDescriptionPatternConfirm.text = getString(R.string.draw_pattern_again)
        patternViewConfirm.setImageRes(R.drawable.ic_icon_eclipse_pattern)
        patternViewConfirm.setColorPath(R.color.color_2D78F4)
        patternViewConfirm.onCheckPattern = { it ->

                val pattern = prefs?.patternPassword
                if (it == pattern) {
                    prefs.isSetupPatternLock = true
                    Toast.makeText(
                        context, getString(R.string.successful_set_pattern_lock),
                        Toast.LENGTH_LONG
                    ).show()
                    activity?.supportFragmentManager?.popBackStack()
                    SetupVoiceLockFragment().pushToScreen(activity as MainActivity)
                } else {
                    Toast.makeText(
                        context, getString(R.string.wrong_pattern_code),
                        Toast.LENGTH_LONG
                    ).show()
                    tvDescriptionPatternConfirm.text = getString(R.string.wrong_pattern_code)
                }
        }
        tvBackPatternLockConfirm.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


}