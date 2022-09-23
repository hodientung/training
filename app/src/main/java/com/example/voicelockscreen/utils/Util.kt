package com.example.voicelockscreen.utils

import android.content.res.Resources
import androidx.fragment.app.Fragment
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModel
import com.example.voicelockscreen.model.DataModelFunction
import com.example.voicelockscreen.model.DataModelTheme
import com.example.voicelockscreen.view.OnboardActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Util {
    companion object {
        const val CUSTOM_PREF_NAME = "Input_data"
        const val THEME_SETTING = "theme_data"
        const val ANSWER_DATA = "answer_data"
        const val ON_BOARDING = "on_boarding"
        const val PATTERN_INPUT = "password_pattern"
        const val PIN_LOCK_CUSTOM_PREF_NAME = "Input_data_pin_lock"
        const val TIMER_PIN_PREF_NAME = "time_pin"
        const val REQ_CODE_SPEECH_INPUT = 100
        const val THE_FIRST_VIEW = 1
        const val THE_SECOND_VIEW = 2
        const val TAG = "ImportantDialogFragment"
        const val KEY_PLAYER_POSITION = "key_player_position"
        const val KEY_PLAYER_PLAY_WHEN_READY = "key_player_play_when_ready"

        fun removeLastChar(text: String?): String? = if (text.isNullOrEmpty())
            text
        else text.substring(0, text.length - 1)

        fun getPassCurrentTime(): String {
            val timePassword =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)
            return timePassword.replace(":", "")
        }

        fun getListTheme(): ArrayList<DataModelTheme> {
            val item = arrayListOf<DataModelTheme>()

            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_1,
                    bg = R.drawable.bg2,
                    imVoice = R.drawable.bg_voice1,
                    colorVoice = R.color.white,
                    bgFunction = R.color.color_F5670E,
                    fontText = R.font.hallo_witchz,
                    colorText = R.color.white,
                    sizeText1 = 30,
                    sizeText2 = 25,
                )
            )
            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_2,
                    bg = R.drawable.background_voice1,
                    imVoice = R.drawable.icon_voice_wina,
                    colorVoice = R.color.white,
                    bgFunction = R.color.color_DB7D1D,
                    fontText = R.font.deep_jungle,
                    colorText = R.color.white,
                    sizeText1 = 40,
                    sizeText2 = 30,
                )
            )
            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_3,
                    bg = R.drawable.bg3,
                    imVoice = R.drawable.bg_voice3,
                    colorVoice = R.color.orange,
                    bgFunction = R.color.color_00FFB4,
                    fontText = R.font.jost_black,
                    colorText = R.color.white,
                    sizeText1 = 24,
                    sizeText2 = 20,
                )
            )
            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_4,
                    bg = R.drawable.bg4,
                    imVoice = R.drawable.bg_voice4,
                    colorVoice = R.color.white,
                    bgFunction = R.color.color_E9D754,
                    fontText = R.font.deep_jungle,
                    colorText = R.color.white,
                    sizeText1 = 40,
                    sizeText2 = 30,
                )
            )
            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_5,
                    bg = R.drawable.bg6,
                    imVoice = R.drawable.bg_voice6,
                    colorVoice = R.color.white,
                    bgFunction = R.color.color_DF5A27,
                    fontText = R.font.deep_jungle,
                    colorText = R.color.color_DF5A27,
                    sizeText1 = 40,
                    sizeText2 = 30,
                )
            )
            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_6,
                    bg = R.drawable.bg5,
                    imVoice = R.drawable.bg_voice5,
                    colorVoice = R.color.white,
                    bgFunction = R.color.color_137045,
                    fontText = R.font.merry_sugar_snow,
                    colorText = R.color.white,
                    sizeText1 = 35,
                    sizeText2 = 20,
                )
            )
            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_7,
                    bg = R.drawable.bg7,
                    imVoice = R.drawable.bg_voice7,
                    colorVoice = R.color.white,
                    bgFunction = R.color.color_815D8A,
                    fontText = R.font.deep_jungle,
                    colorText = R.color.color_815D8A,
                    sizeText1 = 40,
                    sizeText2 = 30,
                )
            )
            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_8,
                    bg = R.drawable.bg9,
                    imVoice = R.drawable.bg_voice9,
                    colorVoice = R.color.white,
                    bgFunction = R.color.color_00BDFF,
                    fontText = R.font.digital_7,
                    colorText = R.color.white,
                    sizeText1 = 36,
                    sizeText2 = 30,
                    fontTextForget = R.font.deep_jungle
                )
            )
            item.add(
                DataModelTheme(
                    colorTheme = R.drawable.theme_9,
                    bg = R.drawable.bg8,
                    imVoice = R.drawable.bg_voice8,
                    colorVoice = R.color.white,
                    bgFunction = R.color.color_A4D498,
                    fontText = R.font.deep_jungle,
                    colorText = R.color.white,
                    sizeText1 = 40,
                    sizeText2 = 30,
                )
            )
            return item
        }

        fun getListNumber(): ArrayList<DataModel> {
            val item = arrayListOf<DataModel>()
            item.add(DataModel(1, "1"))
            item.add(DataModel(1, "2"))
            item.add(DataModel(1, "3"))
            item.add(DataModel(1, "4"))
            item.add(DataModel(1, "5"))
            item.add(DataModel(1, "6"))
            item.add(DataModel(1, "7"))
            item.add(DataModel(1, "8"))
            item.add(DataModel(1, "9"))
            item.add(DataModel(2, "100"))
            item.add(DataModel(1, "0"))
            item.add(DataModel(3, "xoa"))
            return item
        }

        fun Fragment.pushToScreen(activity: MainActivity) {
            activity.supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.content_frame, this).commit()
        }

        fun Fragment.pushToScreenOfOnBoard(activity: OnboardActivity) {
            activity.supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.contentOnboard, this).commit()
        }

        private fun getColorListScreen(): ArrayList<Int> {
            val colorList = arrayListOf<Int>()

            colorList.add(R.drawable.t6)
            colorList.add(R.drawable.t4)
            colorList.add(R.drawable.themec)
            colorList.add(R.drawable.themee)
            colorList.add(R.drawable.themeb)
            colorList.add(R.drawable.thema)
            colorList.add(R.drawable.t1)
            colorList.add(R.drawable.t2)
            colorList.add(R.drawable.t3)
            colorList.add(R.drawable.t7)
            return colorList
        }

        private fun getColorListButton(): ArrayList<Int> {
            val colorList = arrayListOf<Int>()
            colorList.add(R.drawable.round_button)
            colorList.add(R.drawable.round_button2)
            colorList.add(R.drawable.round_button3)
            colorList.add(R.drawable.round_button4)
            colorList.add(R.drawable.round_button5)
            colorList.add(R.drawable.round_button6)
            colorList.add(R.drawable.round_button7)
            colorList.add(R.drawable.round_button8)
            colorList.add(R.drawable.round_button9)
            colorList.add(R.drawable.round_button12)

            return colorList
        }

        fun getThemeToScreen(type: Int): DataModelTheme {
            var backgroundScreen = 0
            var backgroundPinButton = 0
            when (type) {
                0 -> {
                    backgroundScreen = getColorListScreen()[0]
                    backgroundPinButton = getColorListButton()[0]
                }
                1 -> {
                    backgroundScreen = getColorListScreen()[1]
                    backgroundPinButton = getColorListButton()[1]
                }
                2 -> {
                    backgroundScreen = getColorListScreen()[2]
                    backgroundPinButton = getColorListButton()[2]
                }
                3 -> {
                    backgroundScreen = getColorListScreen()[3]
                    backgroundPinButton = getColorListButton()[3]
                }
                4 -> {
                    backgroundScreen = getColorListScreen()[4]
                    backgroundPinButton = getColorListButton()[4]
                }
                5 -> {
                    backgroundScreen = getColorListScreen()[5]
                    backgroundPinButton = getColorListButton()[5]
                }
                6 -> {
                    backgroundScreen = getColorListScreen()[6]
                    backgroundPinButton = getColorListButton()[6]
                }
                7 -> {
                    backgroundScreen = getColorListScreen()[7]
                    backgroundPinButton = getColorListButton()[7]
                }
                8 -> {
                    backgroundScreen = getColorListScreen()[8]
                    backgroundPinButton = getColorListButton()[8]
                }
                9 -> {
                    backgroundScreen = getColorListScreen()[9]
                    backgroundPinButton = getColorListButton()[9]
                }
            }
            return DataModelTheme(backgroundScreen, backgroundPinButton)
        }

        fun timeConversion(value: Long): String {
            val duration = value.toInt()
            val hours = duration / 3600000
            val minute = (duration / 60000) % 60000
            val seconds = duration % 60000 / 1000
            return if (hours > 0)
                String.format("%02d:%02d:%02d", hours, minute, seconds)
            else String.format("%02d:%02d", minute, seconds)
        }

        fun getFunctionAlternativeList(resources: Resources): ArrayList<DataModelFunction> {
            val dataModelFunction = arrayListOf<DataModelFunction>()
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.voice_lock),
                    R.drawable.icon_pin_alternative
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.pattern_lock),
                    R.drawable.icon_pattern_alternative
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.current_time),
                    R.drawable.icon_current_time
                )
            )
            return dataModelFunction
        }

        fun getFunctionList(resources: Resources): ArrayList<DataModelFunction> {
            val dataModelFunction = arrayListOf<DataModelFunction>()
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.voice_lock),
                    R.drawable.icon_voice_lock
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.pin_lock),
                    R.drawable.icon_pin
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.pattern_lock),
                    R.drawable.icon_pattern
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.theme),
                    R.drawable.ic_theme
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.image_gallery),
                    R.drawable.icon_image_gallery
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.video_gallery),
                    R.drawable.ic_video_gallery
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.setting),
                    R.drawable.icon_setting
                )
            )
            return dataModelFunction
        }

    }
}