package com.example.voicelockscreen.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.codeLanguage
import com.example.voicelockscreen.utils.Util
import java.util.*

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferenceHelper.customPreference(this, Util.DATA_LANGUAGE_APP)
        val config = resources.configuration
        prefs.codeLanguage.let {
            val locale = Locale(it)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                config.setLocale(locale)
            else config.locale = locale
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                createConfigurationContext(config)
            resources.updateConfiguration(config,resources.displayMetrics)
        }


    }
}