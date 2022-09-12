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
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetTimerPin
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_set_timer_lock.*


class SetTimerLockFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_timer_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        btnCancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
            Toast.makeText(
                requireContext(),
                getString(R.string.you_clicked_on_cancel),
                Toast.LENGTH_LONG
            ).show()
        }

        btnOk.setOnClickListener {
            val prefs =
                context?.let {
                    PreferenceHelper.customPreference(
                        it,
                        Util.TIMER_PIN_PREF_NAME
                    )
                }
            prefs?.isSetTimerPin = true
            Toast.makeText(
                requireContext(),
                getString(R.string.successfully_set_time_lock),
                Toast.LENGTH_LONG
            ).show()
            activity?.supportFragmentManager?.popBackStack()
            SetupVoiceLockFragment().pushToScreen(activity as MainActivity)
        }
    }


}