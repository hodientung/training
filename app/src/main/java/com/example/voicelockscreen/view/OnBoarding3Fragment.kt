package com.example.voicelockscreen.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isFistOnboard
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.actitvity_onboard.*
import kotlinx.android.synthetic.main.fragment_on_boarding2.*
import kotlinx.android.synthetic.main.fragment_on_boarding3.*


class OnBoarding3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        btn3.setOnClickListener {
            val prefs =
                context?.let {
                    PreferenceHelper.customPreference(
                        it,
                        Util.ON_BOARDING
                    )
                }
            prefs?.isFistOnboard = true
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}