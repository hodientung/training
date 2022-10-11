package com.example.voicelockscreen.view

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.codeLanguage
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isShowTime
import com.example.voicelockscreen.sharepreference.PreferenceHelper.themeCode
import com.example.voicelockscreen.utils.Util
import com.skyfishjy.library.RippleBackground
import java.util.*


class Window(context: Context) {
    var context: Context? = null
    private var mView: View? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var mWindowManager: WindowManager? = null
    private var layoutInflater: LayoutInflater? = null
    private var rippleBackground: RippleBackground? = null

    init {
        this.context = context
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
        rippleBackground = RippleBackground(this.context)
    }

    fun startAnimationRipple() {
        val content3 = mView?.findViewById<RippleBackground>(R.id.content3)
        rippleBackground = content3
        Handler(Looper.getMainLooper()).postDelayed({
            rippleBackground?.startRippleAnimation()
        }, 100)
    }

    fun cancelAnimationRipple() {
        val content3 = mView?.findViewById<RippleBackground>(R.id.content3)
        rippleBackground = content3
        Handler(Looper.getMainLooper()).postDelayed(
            { rippleBackground?.stopRippleAnimation() },
            100
        )
    }


    private fun setTheme() {
        val view1Win = mView?.findViewById<ImageView>(R.id.view1WinLock)
        val view2Win = mView?.findViewById<ImageView>(R.id.view2WinLock)
        val view3Win = mView?.findViewById<ImageView>(R.id.view3WinLock)
        val imBackgroundVoice = mView?.findViewById<ImageView>(R.id.imBackgroundVoiceLock)
        val imv = mView?.findViewById<ImageView>(R.id.imVLock)
        val tvSpeak = mView?.findViewById<TextView>(R.id.tvSpeakLock)
        val tvForget = mView?.findViewById<TextView>(R.id.tvForgetLock)
        val contentAddView = mView?.findViewById<ConstraintLayout>(R.id.content_add_view_window)
        val textDescription = mView?.findViewById<TextView>(R.id.tvTitle)
        val time = mView?.findViewById<TextView>(R.id.tvTime)
        val date = mView?.findViewById<TextView>(R.id.tvDate)
        val ln = mView?.findViewById<LinearLayout>(R.id.ln)
        val context = context
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.THEME_SETTING
                )
            }
        val prefs1 =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.TIME_DEVICE
                )
            }
        if (prefs1?.isShowTime == true)
            ln?.visibility = View.VISIBLE
        else
            ln?.visibility = View.GONE
        val position = prefs?.themeCode
        val dataTheme = position?.let {
            Util.getThemeToScreen(it)
        }
        if (prefs?.themeCode != -1) view1Win?.let {
            view2Win?.let { it1 ->
                view3Win?.let { it2 ->
                    imBackgroundVoice?.let { it3 ->
                        imv?.let { it4 ->
                            tvSpeak?.let { it5 ->
                                tvForget?.let { it6 ->
                                    contentAddView?.let { it7 ->
                                        context?.let { it8 ->
                                            position?.let { it9 ->
                                                textDescription?.let { it10 ->
                                                    time?.let { it11 ->
                                                        date?.let { it12 ->
                                                            Util.setThemeUnlockScreen(
                                                                it,
                                                                it1,
                                                                it2,
                                                                it3,
                                                                it4,
                                                                it5,
                                                                it6,
                                                                it7,
                                                                it10,
                                                                it11,
                                                                it12,
                                                                it8,
                                                                dataTheme,
                                                                it9
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else
            imBackgroundVoice?.let {
                contentAddView?.let { it1 ->
                    textDescription?.let { it2 ->
                        time?.let { it3 ->
                            date?.let { it4 ->
                                tvSpeak?.let { it5 ->
                                    tvForget?.let { it6 ->
                                        Util.setOriginalThemeUnlockScreen(
                                            it,
                                            it1, it2, it3, it4, it5, it6, context
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    fun getView() = mView

    fun open() {
        val prefs = this.let {
            context?.let { it1 ->
                PreferenceHelper.customPreference(
                    it1,
                    Util.DATA_LANGUAGE_APP
                )
            }
        }
        val config = context?.resources?.configuration
        prefs?.codeLanguage?.let {
            val locale = Locale(it)
            config?.setLocale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                config?.let { it1 -> context?.createConfigurationContext(it1) }
            context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)
        }
        try {
            if (mView?.windowToken == null && mView?.parent == null)
                mWindowManager?.addView(mView, mParams)

        } catch (e: Exception) {
            Log.e("Error1", e.toString())
        }
        setTheme()
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