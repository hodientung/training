package com.example.voicelockscreen.view

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.answer
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isCloseWindowSecurityQuestionScreen
import com.example.voicelockscreen.sharepreference.PreferenceHelper.positionAnswer
import com.example.voicelockscreen.utils.Util

class WindowSecurityQuestion(context: Context) {
    private var context: Context? = context
    private var mView: View? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var mWindowManager: WindowManager? = null
    private var layoutInflater: LayoutInflater? = null
    private var position = 0

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
        mView = layoutInflater?.inflate(R.layout.window_forget_password, null)
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

        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.ANSWER_DATA
                )
            }
        val mArrayAdapter = context?.let {
            ArrayAdapter<Any?>(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                it.resources.getStringArray(R.array.questions)
            )
        }
        mArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mView?.findViewById<Spinner>(R.id.spinnerWindow)?.adapter = mArrayAdapter
        mView?.findViewById<Spinner>(R.id.spinnerWindow)?.let {
            it.adapter = mArrayAdapter
            it.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        position = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }
        mView?.findViewById<Button>(R.id.btnSubmitWindow)?.setOnClickListener {
            mView?.findViewById<EditText>(R.id.tvAnswerWindow)?.let {
                val answer = it.text.toString()
                if (answer.isEmpty())
                    it.error = context?.resources?.getString(R.string.no_match_found)
                else {
                    if (answer == prefs?.answer && position == prefs.positionAnswer) {
                        mView?.findViewById<EditText>(R.id.tvAnswerWindow)?.setText("")
                        val prefsSecurity =
                            context?.let { it1 ->
                                PreferenceHelper.customPreference(
                                    it1,
                                    Util.CLOSE_WINDOW
                                )
                            }
                       // prefsSecurity?.isCloseWindowSecurityQuestionScreen = true
                        close()
                    } else it.error = context?.resources?.getString(R.string.no_match_found)
                }
            }
        }
        mView?.findViewById<ImageView>(R.id.tvBackSecurityQuestion)?.setOnClickListener {
            close()
        }
    }

    private fun close() {
        try {

            (context?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.removeView(mView)
            mView?.invalidate()
            (mView?.parent as? ViewGroup)?.removeAllViews()


            (context?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.removeView(
                context?.let {
                    com.example.voicelockscreen.view.Window(
                        it
                    ).getView()
                }
            )
            context?.let {
                com.example.voicelockscreen.view.Window(
                    it
                ).getView()
            }?.invalidate()
            ( context?.let {
                com.example.voicelockscreen.view.Window(
                    it
                ).getView()
            }?.parent as? ViewGroup)?.removeAllViews()
        } catch (e: Exception) {
            Log.e("Error2", e.toString())
        }


    }

}