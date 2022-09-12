package com.example.voicelockscreen.view

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.patternPassword
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import com.itsxtt.patternlock.PatternLockView


class WindowPatternLock(context: Context, private val onClose: () -> Unit) {
    private var context: Context? = context
    private var mView: View? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var mWindowManager: WindowManager? = null
    private var layoutInflater: LayoutInflater? = null

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
        mView = layoutInflater?.inflate(R.layout.window_pattern_lock, null)
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
        setTheme()
        initAction()
    }

    private fun initAction() {
        mView?.findViewById<ImageView>(R.id.tvBackWindowPattern)?.setOnClickListener {
            onBackButton()
        }
        verifyPassword()
    }

    private fun setTheme() {
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.PATTERN_INPUT
                )
            }

        prefs?.themeCode?.let { Util.getThemeToScreen(it).colorTheme }
            ?.let {
                mView?.findViewById<ConstraintLayout>(R.id.content2Window)
                    ?.setBackgroundResource(it)
            }
    }

    private fun verifyPassword() {

        mView?.findViewById<PatternLockView>(R.id.patternViewConfirmWindow)
            ?.setOnPatternListener(object : PatternLockView.OnPatternListener {

                override fun onComplete(ids: ArrayList<Int>): Boolean {
                    /*
                     * A return value required
                     * if the pattern is not correct and you'd like change the pattern to error state, return false
                     * otherwise return true
                     */
                    var valuePatternPassword = ""
                    for (value in ids) {
                        valuePatternPassword += value.toString()
                    }
                    val prefs = context?.let {
                        PreferenceHelper.customPreference(
                            it,
                            Util.PATTERN_INPUT
                        )
                    }
                    if (prefs?.patternPassword == valuePatternPassword)
                        onCloseWhenVerifyPattern()
                    else
                        mView?.findViewById<TextView>(R.id.tvSetPatternCodeConfirmWindow)?.text =
                            context?.getString(R.string.wrong_pattern_code)

                    return true
                }
            })
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

    private fun onCloseWhenVerifyPattern() {
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