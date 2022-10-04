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
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_pin_code.*


class PinCodeFragment : Fragment() {

    private lateinit var mAdapter: RecyclerViewPinLock

    private var isSetupPassword = false

    private var passwordSetup = ""

    //show theme for layout
    private fun setTheme() {
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.THEME_SETTING
                )
            }
        val sizeNumberPin = Util.getListNumber().size
        val listTheme = prefs?.themeCode?.let {
            Util.getThemeToScreen(it)
        }
        if (prefs?.themeCode != -1)
        //setThemeScreen(listTheme, sizeNumberPin)
            Util.setThemeView(
                contentPinCode1,
                tvSetPinCode,
                txtPass,
                tvBackPin,
                imLockPin,
                imVSmall,
                listTheme,
                sizeNumberPin,
                mAdapter.dataModel,
                requireContext()
            )
        else
            Util.setOriginalScreen(
                sizeNumberPin,
                imLockPin,
                imVSmall,
                txtPass,
                tvSetPinCode,
                mAdapter.dataModel,
                requireContext()
            )
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
        setTheme()

    }

    private fun initAction() {
        setUpPassword()
        tvBackPin.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setUpPassword() {
        txtPass.setText("")
        tvSetPinCode.text = getString(R.string.create_new_password)
        Toast.makeText(
            context, getString(R.string.create_new_password),
            Toast.LENGTH_LONG
        ).show()
        mAdapter.onItemClicked = { position ->
            when (position) {
                in 0..8 -> passwordSetup += position.toString()
                10 -> passwordSetup += "9"
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
                if (againInput == passwordSetup) {
                    prefs.isSetupPinLock = true
                    Toast.makeText(
                        context, getString(R.string.successful_set_pin_lock),
                        Toast.LENGTH_LONG
                    ).show()
                    activity?.supportFragmentManager?.popBackStack()
                    SetupVoiceLockFragment().pushToScreen(activity as MainActivity)
                } else {
                    Toast.makeText(
                        context, getString(R.string.wrong_pin_code),
                        Toast.LENGTH_LONG
                    ).show()
                    tvSetPinCode.text = getString(R.string.wrong_pin_code)
                    txtPass.setText("")
                    passwordSetup = ""
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