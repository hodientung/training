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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.codeLanguage
import com.example.voicelockscreen.sharepreference.PreferenceHelper.patternPassword
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import java.util.*


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

    private fun setTheme() {
        val content1 = mView?.findViewById<ConstraintLayout>(R.id.content1)
        val tvDescriptionPatternConfirm =
            mView?.findViewById<TextView>(R.id.tvDescriptionPatternConfirmChangeWindow)
        val tvBackPatternLockConfirm =
            mView?.findViewById<ImageView>(R.id.tvBackPatternLockConfirmChangeWindow)
        val imBackgroundVoicePatternConfirm =
            mView?.findViewById<ImageView>(R.id.imBackgroundVoicePatternConfirmChangeWindow)
        val imVPatternConfirm = mView?.findViewById<ImageView>(R.id.imVPatternConfirmChangeWindow)
        val patternViewConfirm =
            mView?.findViewById<PatternView>(R.id.patternViewConfirmChangeWindow)

        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.THEME_SETTING
                )
            }
        val listTheme = prefs?.themeCode?.let {
            Util.getThemeToScreen(it)
        }
        if (prefs?.themeCode != -1)
            content1?.let {
                tvDescriptionPatternConfirm?.let { it1 ->
                    tvBackPatternLockConfirm?.let { it2 ->
                        imBackgroundVoicePatternConfirm?.let { it3 ->
                            imVPatternConfirm?.let { it4 ->
                                context?.let { it5 ->
                                    patternViewConfirm?.let { it6 ->
                                        Util.setThemePatternView(
                                            it,
                                            it1,
                                            it2,
                                            it3,
                                            it4,
                                            listTheme,
                                            it5,
                                            it6
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        else imBackgroundVoicePatternConfirm?.let {
            tvDescriptionPatternConfirm?.let { it1 ->
                patternViewConfirm?.let { it2 ->
                    context?.let { it3 ->
                        Util.setOriginalPatternScreen(
                            it,
                            it1,
                            it2,
                            it3
                        )
                    }
                }
            }
        }

    }

    fun getView() = mView

    fun open() {
        val prefs = this.let { context?.let { it1 -> PreferenceHelper.customPreference(it1, Util.DATA_LANGUAGE_APP) } }
        val config = context?.resources?.configuration
        prefs?.codeLanguage?.let {
            val locale = Locale(it)
            config?.setLocale(locale)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                config?.let { it1 -> context?.createConfigurationContext(it1) }
            context?.resources?.updateConfiguration(config,context?.resources?.displayMetrics)
        }
        try {
            if (mView?.windowToken == null && mView?.parent == null)
                mWindowManager?.addView(mView, mParams)

        } catch (e: Exception) {
            Log.e("Error1", e.toString())
        }
        //setTheme()
        initAction()
    }

    private fun initAction() {
        setTheme()
        verifyPassword()
        mView?.findViewById<ImageView>(R.id.tvBackPatternLockConfirmChangeWindow)
            ?.setOnClickListener {
                onBackButton()
            }
    }

//    private fun setTheme() {
//        val prefs =
//            context?.let {
//                PreferenceHelper.customPreference(
//                    it,
//                    Util.PATTERN_INPUT
//                )
//            }
//
//        prefs?.themeCode?.let { Util.getThemeToScreen(it).colorTheme }
//            ?.let {
//                mView?.findViewById<ConstraintLayout>(R.id.content2Window)
//                    ?.setBackgroundResource(it)
//            }
//    }

    private fun verifyPassword() {

        val prefs = context?.let {
            PreferenceHelper.customPreference(
                it,
                Util.PATTERN_INPUT
            )
        }
        Toast.makeText(
            context, context?.getString(R.string.draw_your_pattern_to_unlock),
            Toast.LENGTH_LONG
        ).show()
        mView?.findViewById<TextView>(R.id.tvDescriptionPatternConfirmChangeWindow)?.text =
            context?.getString(R.string.draw_your_pattern_to_unlock)
        mView?.findViewById<PatternView>(R.id.patternViewConfirmChangeWindow)?.let {
            it.onCheckPattern = { it ->
                val pattern = prefs?.patternPassword
                if (it == pattern)
                    onCloseWhenVerifyPattern()
                else
                    mView?.findViewById<TextView>(R.id.tvDescriptionPatternConfirmChangeWindow)?.text =
                        context?.getString(R.string.wrong_pattern_code)
            }
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