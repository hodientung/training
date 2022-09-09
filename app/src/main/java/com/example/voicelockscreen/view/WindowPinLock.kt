package com.example.voicelockscreen.view

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.input
import com.example.voicelockscreen.sharepreference.PreferenceHelper.inputPinLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isCloseWindow
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isCloseWindowSecurityQuestionScreen
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themePinButton
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_pin_code.*

class WindowPinLock(context: Context) {
    private var context: Context? = context
    private var mView: View? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var mWindowManager: WindowManager? = null
    private var layoutInflater: LayoutInflater? = null
    private lateinit var mAdapter: RecyclerViewPinLock

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
            )
        }
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        mView = layoutInflater?.inflate(R.layout.window_pin_lock, null)
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
    }

    fun getView() = mView

    fun open() {
        try {
            if (mView?.windowToken == null && mView?.parent == null)
                mWindowManager?.addView(mView, mParams)

        } catch (e: Exception) {
            Log.e("Error1", e.toString())
        }
        initView()
        initAction()

        //bat su kien
//        mView?.findViewById<ImageButton>(R.id.btnSpeakUnlock)?.setOnClickListener {
//
//        }

    }

    private fun initView() {
        mView?.findViewById<RecyclerView>(R.id.rvPinCodeWindow)?.let {
            it.layoutManager = GridLayoutManager(context, 3)
            mAdapter = RecyclerViewPinLock(context)
            mAdapter.dataModel = Util.getListNumber()
            it.adapter = mAdapter
        }
        setTheme()
    }

    private fun initAction() {
        verifyPassword()
    }

    private fun setTheme() {
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.THEME_SETTING
                )
            }

        prefs?.themeCode?.let { Util.getThemeToScreen(it).colorTheme }
            ?.let {
                mView?.findViewById<ConstraintLayout>(R.id.contentPinCodeWindow)
                    ?.setBackgroundResource(it)
            }
        for (i in 0 until Util.getListNumber().size) {
            mAdapter.dataModel[i].backgroundPinButton = prefs?.themePinButton?.let {
                Util.getThemeToScreen(
                    it
                ).colorPinButton
            }
        }
    }

    private fun verifyPassword() {
        var passwordSetup = ""
        mAdapter.onItemClicked = { position ->
            when (position) {
                in 0..8, 10 ->
                    passwordSetup += position.toString()
                else ->
                    passwordSetup = Util.removeLastChar(passwordSetup).toString()
            }
            mView?.findViewById<EditText>(R.id.txtPassWindow)?.setText(passwordSetup)
            val prefs =
                context?.let {
                    PreferenceHelper.customPreference(
                        it,
                        Util.PIN_LOCK_CUSTOM_PREF_NAME
                    )
                }
            if (passwordSetup.length == 4) {
                if (passwordSetup == prefs?.inputPinLock) {
                    val prefsPin =
                        context?.let { it1 ->
                            PreferenceHelper.customPreference(
                                it1,
                                Util.CLOSE_WINDOW_PIN
                            )
                        }
                    prefsPin?.isCloseWindow = true
                    close()
                } else {
                    mView?.findViewById<TextView>(R.id.tvSetPinCodeWindow)?.text =
                        context?.getString(R.string.wrong_pin_code)
                    passwordSetup = ""
                }

                mView?.findViewById<EditText>(R.id.txtPassWindow)?.setText("")
            }
        }
        mView?.findViewById<ImageView>(R.id.tvBackWindow)?.setOnClickListener {
            close()
        }
    }

    private fun close() {
        try {
            (context?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.removeView(mView)
            mView?.invalidate()
            (mView?.parent as? ViewGroup)?.removeAllViews()
        } catch (e: Exception) {
            Log.e("Error2", e.toString())
        }


    }

}