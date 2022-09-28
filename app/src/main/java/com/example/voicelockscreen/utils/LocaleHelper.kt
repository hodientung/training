package com.example.voicelockscreen.utils

import android.content.Context
import android.os.Build
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.codeLanguage
import java.util.*

class LocaleHelper {

    companion object {
        fun setLocale(context: Context, language: String): Context {
            persist(context, language)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return updateResources(context, language);
            }
            return updateResourcesLegacy(context, language);
        }

        private fun updateResourcesLegacy(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = context.resources.configuration
            configuration.locale = locale
            configuration.setLayoutDirection(locale);
            resources.updateConfiguration(configuration, resources.displayMetrics);
            return context
        }

        private fun updateResources(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        }

        fun persist(context: Context, language: String) {
            val prefs = PreferenceHelper.customPreference(context, Util.DATA_LANGUAGE_APP)
            prefs.codeLanguage = language
        }
    }
}