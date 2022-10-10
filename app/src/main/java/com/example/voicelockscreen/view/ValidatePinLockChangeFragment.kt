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
import com.example.voicelockscreen.model.DataModel
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.inputPinLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.placeCursorToEnd
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_validate_pin_lock_change.*

class ValidatePinLockChangeFragment : Fragment() {

    private lateinit var mAdapter: RecyclerViewPinLock
    private var passwordSetup = ""

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
                contentPinCode,
                tvSetPinCodeValidate,
                txtPassValidate,
                tvBackPinValidate,
                imLockPinValidate,
                imVSmallValidate,
                listTheme,
                sizeNumberPin,
                mAdapter.dataModel,
                requireContext()
            )
        else
            Util.setOriginalScreen(
                sizeNumberPin,
                imLockPinValidate,
                imVSmallValidate,
                txtPassValidate,
                tvSetPinCodeValidate,
                mAdapter.dataModel,
                requireContext()
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_validate_pin_lock_change, container, false)
    }

    private fun initAction() {
        setUpPassword()
        tvBackPinValidate.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
        setTheme()
    }

    private fun setUpPassword() {
        Toast.makeText(requireContext(), getString(R.string.enter_old_password), Toast.LENGTH_LONG)
            .show()
        tvSetPinCodeValidate.text = getString(R.string.enter_old_password)
        txtPassValidate.setText("")
        mAdapter.onItemClicked = { position ->
            when (position) {
                in 0..8-> passwordSetup += position.toString()
                10 -> passwordSetup += "9"
                else ->
                    passwordSetup = removeLastChar(passwordSetup).toString()
            }
            txtPassValidate.setText(passwordSetup)
            txtPassValidate.placeCursorToEnd()
            val prefs =
                context?.let {
                    PreferenceHelper.customPreference(
                        it,
                        Util.PIN_LOCK_CUSTOM_PREF_NAME
                    )
                }
            if (passwordSetup.length == 4) {
                if (passwordSetup == prefs?.inputPinLock) {
                    //sua lai fragment
                    activity?.supportFragmentManager?.popBackStack()
                    PinCodeEstablishFragment().pushToScreen(activity as MainActivity)
                } else {
                    txtPassValidate.setText("")
                    passwordSetup = ""
                    tvSetPinCodeValidate.text = getString(R.string.wrong_pin_code)
                    Toast.makeText(context, getString(R.string.wrong_pin_code), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private fun removeLastChar(text: String?): String? = if (text.isNullOrEmpty())
        text
    else text.substring(0, text.length - 1)


    private fun initView() {
        rvPinCodeValidate.layoutManager = GridLayoutManager(context, 3)
        mAdapter = RecyclerViewPinLock(context)
        mAdapter.dataModel = getListNumber()
        rvPinCodeValidate.adapter = mAdapter
    }

    private fun getListNumber(): ArrayList<DataModel> {
        val item = arrayListOf<DataModel>()
        item.add(DataModel(1, "1"))
        item.add(DataModel(1, "2"))
        item.add(DataModel(1, "3"))
        item.add(DataModel(1, "4"))
        item.add(DataModel(1, "5"))
        item.add(DataModel(1, "6"))
        item.add(DataModel(1, "7"))
        item.add(DataModel(1, "8"))
        item.add(DataModel(1, "9"))
        item.add(DataModel(2, "100"))
        item.add(DataModel(1, "0"))
        item.add(DataModel(3, "xoa"))
        return item
    }

}