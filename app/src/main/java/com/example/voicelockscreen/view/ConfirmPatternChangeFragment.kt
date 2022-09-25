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
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_confirm_pattern.*
import kotlinx.android.synthetic.main.fragment_confirm_pattern_change.*


class ConfirmPatternChangeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_pattern_change, container, false)
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
        tvDescriptionPatternConfirmChange.text = getString(R.string.draw_pattern_again)
        patternViewConfirmChange.setImageRes(R.drawable.ic_icon_eclipse_pattern)
        patternViewConfirmChange.setColorPath(R.color.color_2D78F4)
        patternViewConfirmChange.onCheckPattern = { it ->

            val pattern = prefs?.patternPassword
            if (it == pattern) {
                prefs.isSetupPatternLock = true
                Toast.makeText(
                    context, getString(R.string.successful_set_pattern_lock),
                    Toast.LENGTH_LONG
                ).show()
                activity?.supportFragmentManager?.popBackStack()
            } else {
                Toast.makeText(
                    context, getString(R.string.wrong_pattern_code),
                    Toast.LENGTH_LONG
                ).show()
                tvDescriptionPatternConfirmChange.text = getString(R.string.wrong_pattern_code)
            }
        }
        tvBackPatternLockConfirmChange.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}