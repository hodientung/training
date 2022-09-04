package com.example.voicelockscreen.utils

import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelTheme

class Util {
    companion object {
        const val CUSTOM_PREF_NAME = "Input_data"
        const val THEME_SETTING = "theme_data"
        const val PIN_LOCK_CUSTOM_PREF_NAME = "Input_data_pin_lock"
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
        const val KEY_PLAYER_POSITION = "key_player_position"
        const val KEY_PLAYER_PLAY_WHEN_READY = "key_player_play_when_ready"

        private fun getColorListScreen(): ArrayList<Int> {
            var colorList = arrayListOf<Int>()
            colorList.add(R.color.bright_pink)
            colorList.add(R.color.red)
            colorList.add(R.color.orange)
            colorList.add(R.color.yellow)
            colorList.add(R.color.chartreuse)
            colorList.add(R.color.green)
            colorList.add(R.color.spring_green)
            colorList.add(R.color.cyan)
            colorList.add(R.color.azure)
            colorList.add(R.color.blue)
            return colorList
        }

        private fun getColorListButton(): ArrayList<Int> {
            var colorList = arrayListOf<Int>()
            colorList.add(R.color.blue)
            colorList.add(R.color.azure)
            colorList.add(R.color.cyan)
            colorList.add(R.color.spring_green)
            colorList.add(R.color.green)
            colorList.add(R.color.chartreuse)
            colorList.add(R.color.yellow)
            colorList.add(R.color.orange)
            colorList.add(R.color.red)
            colorList.add(R.color.bright_pink)
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

    }
}