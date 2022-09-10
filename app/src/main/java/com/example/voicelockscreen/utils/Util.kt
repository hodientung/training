package com.example.voicelockscreen.utils

import android.content.res.Resources
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModel
import com.example.voicelockscreen.model.DataModelFunction
import com.example.voicelockscreen.model.DataModelTheme
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Util {
    companion object {
        const val CUSTOM_PREF_NAME = "Input_data"
        const val THEME_SETTING = "theme_data"
        const val ANSWER_DATA = "answer_data"
        const val PIN_LOCK_CUSTOM_PREF_NAME = "Input_data_pin_lock"
        const val TIMER_PIN_PREF_NAME = "time_pin"
        const val CLOSE_WINDOW = "close_window"
        const val CLOSE_WINDOW_PIN = "close_window_pin"
        const val STATE_SERVICE_CUSTOM_PREF_NAME = "Input_data_pin_lock"
        const val LANGUAGE = "ja"
        const val timeout = 20001
        const val PERM_REQUEST_CODE_DRAW_OVERLAYS = 1234
        const val REQ_CODE_SPEECH_INPUT = 100
        const val REQ_CODE_PIN_LOCK = 101
        const val THE_FIRST_VIEW = 1
        const val THE_SECOND_VIEW = 2
        const val THE_THIRD_VIEW = 3
        const val ACTION_THEME = "data_theme"
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

        private fun getColorListScreen(): ArrayList<Int> {
            val colorList = arrayListOf<Int>()
            colorList.add(R.drawable.background_girl1)
            colorList.add(R.drawable.girl)
            colorList.add(R.drawable.background1)
            colorList.add(R.drawable.background2)
            colorList.add(R.drawable.background3)
            colorList.add(R.drawable.background4)
            colorList.add(R.drawable.background5)
            colorList.add(R.drawable.background16)
            colorList.add(R.drawable.background7)
            colorList.add(R.drawable.background8)
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
            var videoTime = ""
            val duration = value.toInt()
            val hours = duration / 3600000
            val minute = (duration / 60000) % 60000
            val seconds = duration % 60000 / 1000
            videoTime = if (hours > 0)
                String.format("%02d:%02d:%02d", hours, minute, seconds)
            else String.format("%02d:%02d", minute, seconds)
            return videoTime
        }

        fun getFunctionAlternativeList(resources: Resources): ArrayList<DataModelFunction> {
            val dataModelFunction = arrayListOf<DataModelFunction>()
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.voice_lock),
                    R.drawable.ic_sharp_mic_none_24
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.current_time),
                    R.drawable.ic_round_watch_later_24
                )
            )
            return dataModelFunction
        }

        fun getFunctionList(resources: Resources): ArrayList<DataModelFunction> {
            val dataModelFunction = arrayListOf<DataModelFunction>()
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.voice_lock),
                    R.drawable.ic_sharp_mic_none_24
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.pin_lock),
                    R.drawable.ic_sharp_lock_open_24
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.pattern_lock),
                    R.drawable.ic_sharp_pattern_24
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.theme),
                    R.drawable.ic_baseline_bubble_chart_24
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.image_gallery),
                    R.drawable.ic_baseline_image_search_24
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.video_gallery),
                    R.drawable.ic_baseline_video_library_24
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.preview),
                    R.drawable.ic_sharp_preview_24
                )
            )
            dataModelFunction.add(
                DataModelFunction(
                    resources.getString(R.string.setting),
                    R.drawable.ic_baseline_settings_24
                )
            )
            return dataModelFunction
        }

    }
}