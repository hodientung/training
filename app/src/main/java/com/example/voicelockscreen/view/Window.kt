package com.example.voicelockscreen.view

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*

import android.widget.Button
import android.widget.ImageButton
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R


class Window(context: Context) {
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
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        mView = layoutInflater?.inflate(R.layout.fragment_voice_unlock, null)
        mWindowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager?
    }

    fun getView() = mView
    fun getContext() = context

    fun open() {
        try {
            if (mView?.windowToken == null && mView?.parent == null)
                mWindowManager?.addView(mView, mParams)

        } catch (e: Exception) {
            Log.e("Error1", e.toString())
        }
    }

    fun close() {
        try {
            (context?.getSystemService(WINDOW_SERVICE) as? WindowManager)?.removeView(mView)
            mView?.invalidate()
            (mView?.parent as? ViewGroup)?.removeAllViews()
        } catch (e: Exception) {
            Log.e("Error2", e.toString())
        }


    }

}