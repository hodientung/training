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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetTimerPin
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themePinButton
import com.example.voicelockscreen.utils.Util

class WindowTimerPin(context: Context, private val onClose: () -> Unit) {
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
        mView = layoutInflater?.inflate(R.layout.window_timer_pin, null)
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
    }

    private fun initView() {
        mView?.findViewById<RecyclerView>(R.id.rvTimerPinWindow)?.let {
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
                mView?.findViewById<ConstraintLayout>(R.id.contentTimerPinWindow)
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
                    passwordSetup += (position + 1).toString()
                else ->
                    passwordSetup = Util.removeLastChar(passwordSetup).toString()
            }
            mView?.findViewById<EditText>(R.id.txtPassTimerPinWindow)?.setText(passwordSetup)
            val prefs =
                context?.let {
                    PreferenceHelper.customPreference(
                        it,
                        Util.TIMER_PIN_PREF_NAME
                    )
                }
            if (passwordSetup.length == 4) {
                if (prefs?.isSetTimerPin == true && passwordSetup == Util.getPassCurrentTime()) {
                    onCloseWhenVerifyPin()
                } else {
                    mView?.findViewById<TextView>(R.id.tvEnterYourPassword)?.text =
                        context?.getString(R.string.wrong_pin_code_timer)
                    passwordSetup = ""
                }

                mView?.findViewById<EditText>(R.id.txtPassTimerPinWindow)?.setText("")
            }
        }
        mView?.findViewById<ImageView>(R.id.tvBackTimerPin)?.setOnClickListener {
            onBackButton()
        }
    }

    private fun onBackButton() {
        try {
            (context?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.removeView(mView)
            mView?.invalidate()
            (mView?.parent as? ViewGroup)?.removeAllViews()
        } catch (e: Exception) {
            Log.e("Error2", e.toString())
        }
    }

    private fun onCloseWhenVerifyPin() {
        try {
            (context?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.removeView(mView)
            mView?.invalidate()
            (mView?.parent as? ViewGroup)?.removeAllViews()
            onClose.invoke()
        } catch (e: Exception) {
            Log.e("Error2", e.toString())
        }
    }

}