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
import com.example.voicelockscreen.sharepreference.PreferenceHelper.inputPinLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util

class WindowPinLock(context: Context, private val onClose: () -> Unit) {
    private var context: Context? = context
    private var mView: View? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var mWindowManager: WindowManager? = null
    private var layoutInflater: LayoutInflater? = null
    private lateinit var mAdapter: RecyclerViewPinLock
    private var passwordSetup = ""

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


    private fun setTheme() {
        val contentPinCode = mView?.findViewById<ConstraintLayout>(R.id.contentPinCodeWindow)
        val tvSetPinCodeEstablish = mView?.findViewById<TextView>(R.id.tvSetPinCodeWindow)
        val txtPassEstablish = mView?.findViewById<EditText>(R.id.txtPassWindow)
        val tvBackPinEstablish = mView?.findViewById<ImageView>(R.id.tvBackPinWindow)
        val imLockPinEstablish = mView?.findViewById<ImageView>(R.id.imLockPinWindow)
        val imVSmallEstablish = mView?.findViewById<ImageView>(R.id.imVSmallWindow)

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
            contentPinCode?.let {
                tvSetPinCodeEstablish?.let { it1 ->
                    txtPassEstablish?.let { it2 ->
                        tvBackPinEstablish?.let { it3 ->
                            imLockPinEstablish?.let { it4 ->
                                imVSmallEstablish?.let { it5 ->
                                    context?.let { it6 ->
                                        Util.setThemeView(
                                            it,
                                            it1,
                                            it2,
                                            it3,
                                            it4,
                                            it5,
                                            listTheme,
                                            sizeNumberPin,
                                            mAdapter.dataModel,
                                            it6
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        else
            imLockPinEstablish?.let {
                txtPassEstablish?.let { it1 ->
                    context?.let { it2 ->
                        tvSetPinCodeEstablish?.let { it3 ->
                            Util.setOriginalScreen(
                                sizeNumberPin,
                                it,
                                it1,
                                it3,
                                mAdapter.dataModel,
                                it2
                            )
                        }
                    }
                }
            }
    }

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

    private fun verifyPassword() {
        mView?.findViewById<EditText>(R.id.txtPassWindow)?.setText("")
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
                if (passwordSetup == prefs?.inputPinLock)
                    onCloseWhenVerifyPin()
                else {
                    mView?.findViewById<TextView>(R.id.tvSetPinCodeWindow)?.text =
                        context?.getString(R.string.wrong_pin_code)
                    passwordSetup = ""
                    mView?.findViewById<EditText>(R.id.txtPassWindow)?.setText("")
                }
            }
        }
        mView?.findViewById<ImageView>(R.id.tvBackPinWindow)?.setOnClickListener {
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