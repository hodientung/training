package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.inputPinLock
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_pincode_establish.*


class PinCodeEstablishFragment : Fragment() {
    private lateinit var mAdapter: RecyclerViewPinLock

    private var isSetupPassword = false

    private var passwordSetup = ""


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
//            ?.let { contentEstablishSetup.setBackgroundResource(it) }
//        for (i in 0 until Util.getListNumber().size) {
//            mAdapter.dataModel[i].backgroundPinButton = prefs?.themePinButton?.let {
//                Util.getThemeToScreen(
//                    it
//                ).colorPinButton
//            }
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pincode_establish, container, false)
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
        tvBackPinEstablish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setUpPassword() {
        txtPassEstablish.setText("")
        tvSetPinCodeEstablish.text =  getString(R.string.enter_new_password)
        Toast.makeText(requireContext(), getString(R.string.enter_new_password), Toast.LENGTH_LONG)
            .show()
        mAdapter.onItemClicked = { position ->
            when (position) {
                in 0..8, 10 ->
                    passwordSetup += position.toString()
                else ->
                    passwordSetup = removeLastChar(passwordSetup).toString()
            }
            txtPassEstablish.setText(passwordSetup)
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
                tvSetPinCodeEstablish.text = getString(R.string.enter_pin_code_again)
                txtPassEstablish.setText("")
                isSetupPassword = true
                passwordSetup = ""

            }
            if (isSetupPassword && passwordSetup.length == 4) {
                val againInput = prefs?.inputPinLock
                if (againInput == passwordSetup) {
                    Toast.makeText(
                        context, getString(R.string.successful_set_pin_lock),
                        Toast.LENGTH_LONG
                    ).show()
                    activity?.supportFragmentManager?.popBackStack()
                } else {
                    Toast.makeText(
                        context, getString(R.string.wrong_pin_code),
                        Toast.LENGTH_LONG
                    ).show()
                    passwordSetup = ""
                    txtPassEstablish.setText("")
                }
            }
        }
    }


    private fun removeLastChar(text: String?): String? = if (text.isNullOrEmpty())
        text
    else text.substring(0, text.length - 1)


    private fun initView() {
        rvPinCodeEstablish.layoutManager = GridLayoutManager(context, 3)
        mAdapter = RecyclerViewPinLock(context)
        mAdapter.dataModel = Util.getListNumber()
        rvPinCodeEstablish.adapter = mAdapter
    }
}