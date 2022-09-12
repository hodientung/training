package com.example.voicelockscreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.inputPinLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupPinLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themePinButton
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_pin_code.*


class PinCodeFragment : Fragment() {

    private lateinit var mAdapter: RecyclerViewPinLock

    private var isSetupPassword = false

    private var passwordSetup = ""

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
            ?.let { contentPinCode.setBackgroundResource(it) }
        for (i in 0 until Util.getListNumber().size) {
            mAdapter.dataModel[i].backgroundPinButton = prefs?.themePinButton?.let {
                Util.getThemeToScreen(
                    it
                ).colorPinButton
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()

    }

    private fun initAction() {
//        mAdapter.onItemClicked = {
//            Toast.makeText(context, "vi tri $it", Toast.LENGTH_SHORT).show()
//        }
        setUpPassword()
        tvBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }


    private fun setUpPassword() {
        mAdapter.onItemClicked = { position ->
            when (position) {
                in 0..8, 10 ->
                    passwordSetup += position.toString()
                else ->
                    passwordSetup = Util.removeLastChar(passwordSetup).toString()
            }
            txtPass.setText(passwordSetup)
            val prefs =
                context?.let {
                    PreferenceHelper.customPreference(
                        it,
                        Util.PIN_LOCK_CUSTOM_PREF_NAME
                    )
                }
            if (passwordSetup.length == 4 && !isSetupPassword) {
                Toast.makeText(
                    context, getString(R.string.enter_pin_code_again),
                    Toast.LENGTH_LONG
                ).show()
                prefs?.inputPinLock = passwordSetup
                tvSetPinCode.text = getString(R.string.enter_pin_code_again)
                txtPass.setText("")
                isSetupPassword = true
                passwordSetup = ""

            }
            if (isSetupPassword && passwordSetup.length == 4) {
                val againInput = prefs?.inputPinLock
                prefs?.isSetupPinLock = true
                if (againInput == passwordSetup) {
                    Toast.makeText(
                        context, getString(R.string.successful_set_pin_lock),
                        Toast.LENGTH_LONG
                    ).show()
                    activity?.supportFragmentManager?.popBackStack()
                    SetupVoiceLockFragment().pushToScreen(activity as MainActivity)
                }
            }
        }
    }

    private fun initView() {
        rvPinCode.layoutManager = GridLayoutManager(context, 3)
        mAdapter = RecyclerViewPinLock(context)
        mAdapter.dataModel = Util.getListNumber()
        rvPinCode.adapter = mAdapter
    }
}