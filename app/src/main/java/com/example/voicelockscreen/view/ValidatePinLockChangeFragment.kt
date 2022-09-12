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
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themePinButton
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_validate_pin_lock_change.*

class ValidatePinLockChangeFragment : Fragment() {

    private lateinit var mAdapter: RecyclerViewPinLock
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
            ?.let { contentValidatePinLock.setBackgroundResource(it) }
        for (i in 0 until getListNumber().size) {
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
        return inflater.inflate(R.layout.fragment_validate_pin_lock_change, container, false)
    }

    private fun initAction() {
//        mAdapter.onItemClicked = {
//            Toast.makeText(context, "vi tri $it", Toast.LENGTH_SHORT).show()
//        }
        setUpPassword()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()

    }

    private fun setUpPassword() {
        mAdapter.onItemClicked = { position ->
            when (position) {
                in 0..8, 10 ->
                    passwordSetup += position.toString()
                else ->
                    passwordSetup = removeLastChar(passwordSetup).toString()
            }
            txtPassValidate.setText(passwordSetup)
            val prefs =
                context?.let {
                    PreferenceHelper.customPreference(
                        it,
                        Util.PIN_LOCK_CUSTOM_PREF_NAME
                    )
                }
            if (passwordSetup.length == 4) {
                if (passwordSetup == prefs?.inputPinLock) {
                    Toast.makeText(
                        context, "Exchange Screen",
                        Toast.LENGTH_LONG
                    ).show()

                    //sua lai fragment
                    PinCodeEstablishFragment().pushToScreen(activity as MainActivity)
                } else {
                    txtPassValidate.setText("")
                    passwordSetup = ""
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