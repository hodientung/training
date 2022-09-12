package com.example.voicelockscreen.sharepreference

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val INPUT = "input"
    private const val CODE_THEME = "code_theme"
    private const val POSITION_ANSWER = "position_answer"
    private const val CODE_THEME_BUTTON = "code_theme_button"
    private const val ON_SERVICE = "on_service"
    private const val CHECK_TIMER_PIN = "check_timer_pin"
    private const val SETUP_VOICE_LOCK = "setup_voice_lock"
    private const val SETUP_PIN_LOCK = "setup_pin_lock"
    private const val SETUP_PATTERN_LOCK = "setup_pattern_lock"
    private const val INPUT_PIN_LOCK = "input_pin_lock"
    private const val ANSWER = "answer"
    private const val INPUT_PATTERN = "input_pattern"

    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    // custom put du lieu vao share preference
    fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
        val key = pair.first
        when (val value = pair.second) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }

//    var SharedPreferences.userId
//        get() = getInt(USER_ID, 0)
//        set(value) {
//            editMe {
//                it.putInt(USER_ID, value)
//            }
//        }

    var SharedPreferences.input
        get() = getString(INPUT, "")
        set(value) {
            editMe {
                it.putString(INPUT, value)
            }
        }

    var SharedPreferences.inputPinLock
        get() = getString(INPUT_PIN_LOCK, "")
        set(value) {
            editMe {
                it.putString(INPUT_PIN_LOCK, value)
            }
        }

    var SharedPreferences.onService
        get() = getBoolean(ON_SERVICE, false)
        set(value) {
            editMe {
                it.putBoolean(ON_SERVICE, value)
            }
        }

    var SharedPreferences.isSetupVoiceLock
        get() = getBoolean(SETUP_VOICE_LOCK, false)
        set(value) {
            editMe {
                it.putBoolean(SETUP_VOICE_LOCK, value)
            }
        }
    var SharedPreferences.isSetupPinLock
        get() = getBoolean(SETUP_PIN_LOCK, false)
        set(value) {
            editMe {
                it.putBoolean(SETUP_PIN_LOCK, value)
            }
        }
    var SharedPreferences.isSetupPatternLock
        get() = getBoolean(SETUP_PATTERN_LOCK, false)
        set(value) {
            editMe {
                it.putBoolean(SETUP_PATTERN_LOCK, value)
            }
        }

    var SharedPreferences.themeCode
        get() = getInt(CODE_THEME, 0)
        set(value) {
            editMe {
                it.putInt(CODE_THEME, value)
            }
        }

    var SharedPreferences.themePinButton
        get() = getInt(CODE_THEME_BUTTON, 0)
        set(value) {
            editMe {
                it.putInt(CODE_THEME_BUTTON, value)
            }
        }

    var SharedPreferences.answer
        get() = getString(ANSWER, "")
        set(value) {
            editMe {
                it.putString(ANSWER, value)
            }
        }
    var SharedPreferences.patternPassword
        get() = getString(INPUT_PATTERN, "")
        set(value) {
            editMe {
                it.putString(INPUT_PATTERN, value)
            }
        }

    var SharedPreferences.positionAnswer
        get() = getInt(POSITION_ANSWER, 0)
        set(value) {
            editMe {
                it.putInt(POSITION_ANSWER, value)
            }
        }

    var SharedPreferences.isSetTimerPin
        get() = getBoolean(CHECK_TIMER_PIN, false)
        set(value) {
            editMe {
                it.putBoolean(CHECK_TIMER_PIN, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }

}