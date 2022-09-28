package com.example.voicelockscreen.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.*
import com.example.voicelockscreen.view.OnboardActivity
import com.example.voicelockscreen.view.PatternView
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
        const val TIME_DEVICE = "current_time_device"
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

        fun getPassCurrentTimeLock(): String {
            return SimpleDateFormat(
                "HH:mm a",
                Locale.getDefault()
            ).format(Calendar.getInstance().time)
        }

        fun getPassCurrentDateLock(): String {
            return SimpleDateFormat(
                "E",
                Locale.getDefault()
            ).format(Calendar.getInstance().time) + ", " + SimpleDateFormat(
                "dd MMMM yyyy ",
                Locale.getDefault()
            ).format(Calendar.getInstance().time)
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

        fun getListItemSetting(context: Context): ArrayList<DataModelSetting> {
            val item = arrayListOf<DataModelSetting>()
            item.add(
                DataModelSetting(
                    R.drawable.icon_lock,
                    context.getString(R.string.disable_system_lock)
                )
            )
            item.add(
                DataModelSetting(
                    R.drawable.icon_mail,
                    context.getString(R.string.hidden_date)
                )
            )
            item.add(
                DataModelSetting(
                    R.drawable.icon_language,
                    context.getString(R.string.language)
                )
            )
            item.add(DataModelSetting(R.drawable.icon_share, context.getString(R.string.share)))
            item.add(DataModelSetting(R.drawable.icon_star, context.getString(R.string.rate_us)))
            item.add(DataModelSetting(R.drawable.icon_privacy, context.getString(R.string.privacy)))
            item.add(DataModelSetting(R.drawable.icon_about, context.getString(R.string.about)))
            return item
        }

        fun getListItemLanguage(context: Context): ArrayList<DataModelSetting> {
            val item = arrayListOf<DataModelSetting>()
            item.add(
                DataModelSetting(
                    R.drawable.c1,
                    context.getString(R.string.english),
                    code = "en"
                )
            )
            item.add(                        //"en", "hi", "es", "fr", "de", "su", "pt"
                DataModelSetting(
                    R.drawable.c2,
                    context.getString(R.string.hindi),
                    code = "hi"
                )
            )
            item.add(
                DataModelSetting(
                    R.drawable.c3,
                    context.getString(R.string.spanish), code = "es"
                )
            )
            item.add(
                DataModelSetting(
                    R.drawable.c4,
                    context.getString(R.string.french),
                    code = "fr"
                )
            )
            item.add(
                DataModelSetting(
                    R.drawable.c5,
                    context.getString(R.string.german),
                    code = "de"
                )
            )
            item.add(
                DataModelSetting(
                    R.drawable.c6,
                    context.getString(R.string.indonesian),
                    code = "su"
                )
            )
            item.add(
                DataModelSetting(
                    R.drawable.c7,
                    context.getString(R.string.portuguese),
                    code = "pt"
                )
            )
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
            var colorBack = 0
            var fontText = 0
            var colorText = 0
            var largeImage = 0
            var smallImage = 0
            var colorInputPin = 0
            var iconPin = 0
            var iconPattern = 0
            var sizeText1 = 0
            var sizeText2 = 0
            var fontTextForget = 0
            var colorPath = 0
            var imVoice = 0
            var bg = 0
            var colorVoice = 0
            var bgFunction = 0
            var colorDelete: Int? = 0

            when (type) {
                0 -> {
                    colorBack = R.color.white
                    fontText = R.font.deep_jungle
                    colorText = R.color.white
                    largeImage = R.drawable.large1
                    smallImage = R.drawable.ic_small1
                    iconPin = R.drawable.icon_pin1
                    iconPattern = R.drawable.icon_pattern1
                    colorInputPin = R.color.color_F5670E
                    sizeText1 = 30
                    sizeText2 = 25
                    colorPath = R.color.color_EB5005
                    bg = R.drawable.bg2
                    imVoice = R.drawable.bg_voice1
                    colorVoice = R.color.white
                    bgFunction = R.color.color_F5670E
                    colorDelete = R.color.color_F5670E

                }
                1 -> {
                    colorBack = R.color.white
                    fontText = R.font.deep_jungle
                    colorText = R.color.white
                    largeImage = R.drawable.large2
                    smallImage = R.drawable.small2
                    iconPin = R.drawable.icon_pin2
                    iconPattern = R.drawable.icon_pattern2
                    colorInputPin = R.color.color_DF5A27
                    sizeText1 = 40
                    sizeText2 = 30
                    colorPath = R.color.color_EB5005
                    bg = R.drawable.background_voice1
                    imVoice = R.drawable.icon_voice_wina
                    colorVoice = R.color.white
                    bgFunction = R.color.color_DB7D1D
                    colorDelete = R.color.color_DB7D1D
                }
                2 -> {
                    colorBack = R.color.white
                    fontText = R.font.jost_black
                    colorText = R.color.white
                    largeImage = R.drawable.large3
                    smallImage = R.drawable.ic_small1
                    iconPin = R.drawable.icon_pin3
                    iconPattern = R.drawable.icon_patter3
                    colorInputPin = R.color.color_00FFB4
                    sizeText1 = 24
                    sizeText2 = 20
                    colorPath = R.color.color_00FFB4
                    bg = R.drawable.bg3
                    imVoice = R.drawable.bg_voice3
                    colorVoice = R.color.orange
                    bgFunction = R.color.color_00FFB4
                    colorDelete = R.color.color_00FFB4
                }
                3 -> {
                    colorBack = R.color.white
                    fontText = R.font.deep_jungle
                    colorText = R.color.white
                    largeImage = R.drawable.large4
                    smallImage = R.drawable.small4
                    iconPin = R.drawable.icon_pin4
                    iconPattern = R.drawable.icon_pattern4
                    colorInputPin = R.color.color_00FFB4
                    sizeText1 = 40
                    sizeText2 = 30
                    colorPath = R.color.color_E9D754
                    bg = R.drawable.bg4
                    imVoice = R.drawable.bg_voice4
                    colorVoice = R.color.white
                    bgFunction = R.color.color_E9D754
                    colorDelete = R.color.color_E9D754
                }
                4 -> {
                    colorBack = R.color.white
                    fontText = R.font.deep_jungle
                    colorText = R.color.white
                    largeImage = R.drawable.large5
                    smallImage = R.drawable.ic_small1
                    iconPin = R.drawable.icon_pin5
                    iconPattern = R.drawable.icon_pattern5
                    colorInputPin = R.color.color_FD6D04
                    sizeText1 = 40
                    sizeText2 = 30
                    colorPath = R.color.color_FD6D04
                    bg = R.drawable.bg6
                    imVoice = R.drawable.bg_voice6
                    colorVoice = R.color.white
                    bgFunction = R.color.color_DF5A27
                    colorDelete = R.color.color_DF5A27
                }
                5 -> {
                    colorBack = R.color.white
                    fontText = R.font.merry_sugar_snow
                    colorText = R.color.white
                    largeImage = R.drawable.large6
                    smallImage = R.drawable.small4
                    iconPin = R.drawable.icon_pin6
                    iconPattern = R.drawable.icon_pattern6
                    colorInputPin = R.color.color_FD6D04
                    sizeText1 = 35
                    sizeText2 = 20
                    colorPath = R.color.color_137045
                    bg = R.drawable.bg5
                    imVoice = R.drawable.bg_voice5
                    colorVoice = R.color.white
                    bgFunction = R.color.color_137045
                    colorDelete = R.color.color_137045
                }
                6 -> {
                    colorBack = R.color.white
                    fontText = R.font.deep_jungle
                    colorText = R.color.white
                    largeImage = R.drawable.bg_voice7
                    smallImage = R.drawable.ic_small1
                    iconPin = R.drawable.icon_pin7
                    iconPattern = R.drawable.icon_pattern7
                    colorInputPin = R.color.color_815D8A
                    sizeText1 = 40
                    sizeText2 = 30
                    colorPath = R.color.color_815D8A
                    bg = R.drawable.bg7
                    imVoice = R.drawable.bg_voice7
                    colorVoice = R.color.white
                    bgFunction = R.color.color_815D8A
                    colorDelete = R.color.color_815D8A
                }
                7 -> {
                    colorBack = R.color.white
                    fontText = R.font.digital_7
                    colorText = R.color.white
                    largeImage = R.drawable.bg_voice9
                    smallImage = R.drawable.ic_small1
                    iconPin = R.drawable.icon_pin8
                    iconPattern = R.drawable.icon_pin8
                    colorInputPin = R.color.color_00BDFF
                    sizeText1 = 36
                    sizeText2 = 30
                    colorPath = R.color.color_00BDFF
                    bg = R.drawable.bg9
                    imVoice = R.drawable.bg_voice9
                    colorVoice = R.color.white
                    bgFunction = R.color.color_00BDFF
                    fontTextForget = R.font.deep_jungle
                    colorDelete = R.color.color_00BDFF
                }
                8 -> {
                    colorBack = R.color.white
                    fontText = R.font.deep_jungle
                    colorText = R.color.white
                    largeImage = R.drawable.large9
                    smallImage = R.drawable.ic_small1
                    iconPin = R.drawable.icon_pin9
                    iconPattern = R.drawable.icon_pattern9
                    colorInputPin = R.color.color_A4D498
                    sizeText1 = 40
                    sizeText2 = 30
                    colorPath = R.color.color_A4D498
                    bg = R.drawable.bg8
                    imVoice = R.drawable.bg_voice8
                    colorVoice = R.color.white
                    bgFunction = R.color.color_A4D498
                    colorDelete = R.color.color_A4D498
                }
            }
            return DataModelTheme(
                colorBack = colorBack,
                fontText = fontText,
                colorText = colorText,
                largeImage = largeImage,
                smallImage = smallImage,
                iconPin = iconPin,
                iconPattern = iconPattern,
                colorInputPin = colorInputPin,
                sizeText1 = sizeText1,
                sizeText2 = sizeText2,
                colorPath = colorPath,
                bg = bg,
                imVoice = imVoice,
                colorVoice = colorVoice,
                bgFunction = bgFunction,
                fontTextForget = fontTextForget,
                colorDelete = colorDelete
            )
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

        fun View.setMargins(
            left: Int? = null,
            top: Int? = null,
            right: Int? = null,
            bottom: Int? = null
        ) {
            val lp = layoutParams as? ViewGroup.MarginLayoutParams
                ?: return

            lp.setMargins(
                left ?: lp.leftMargin,
                top ?: lp.topMargin,
                right ?: lp.rightMargin,
                bottom ?: lp.bottomMargin
            )

            layoutParams = lp
        }

        fun setThemePatternView(
            contentPinCode1: View, tvSetPinCode: View, tvBackPin: View, imLockPin: View,
            imVSmall: View, listTheme: DataModelTheme?, context: Context, patternView: View
        ) {
            listTheme?.bg?.let { contentPinCode1.setBackgroundResource(it) }
            listTheme?.colorText?.let {
                (tvSetPinCode as TextView).setTextColor(
                    ContextCompat.getColor(
                        context,
                        it
                    )
                )
            }
            listTheme?.fontText?.let {
                (tvSetPinCode as TextView).typeface = ResourcesCompat.getFont(context, it)
            }
            (tvSetPinCode as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
            listTheme?.colorBack?.let {
                (tvBackPin as ImageView).setColorFilter(
                    ContextCompat.getColor(
                        context,
                        it
                    )
                )
            }
            listTheme?.largeImage?.let { imLockPin.setBackgroundResource(it) }
            listTheme?.smallImage?.let { imVSmall.setBackgroundResource(it) }
            listTheme?.iconPattern?.let { (patternView as PatternView).setImageRes(it) }
            listTheme?.colorPath?.let { (patternView as PatternView).setColorPath(it) }
        }

        fun setOriginalPatternScreen(
            imLockPin: View,
            tvSetPinCode: View,
            patternView: View,
        ) {
            (imLockPin as ImageView).setBackgroundResource(R.drawable.ic_icon_frament_pattern)
            (tvSetPinCode as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            (patternView as PatternView).setColorPath(R.color.color_2D78F4)
            patternView.setImageRes(R.drawable.ic_icon_eclipse_pattern)
        }

        fun setThemeView(
            contentPinCode1: View,
            tvSetPinCode: View,
            txtPass: View,
            tvBackPin: View,
            imLockPin: View,
            imVSmall: View,
            listTheme: DataModelTheme?,
            sizeNumberPin: Int,
            dataModel: ArrayList<DataModel>,
            context: Context
        ) {
            listTheme?.bg?.let { contentPinCode1.setBackgroundResource(it) }
            listTheme?.colorText?.let {
                (tvSetPinCode as TextView).setTextColor(
                    ContextCompat.getColor(
                        context,
                        it
                    )
                )
            }
            listTheme?.colorInputPin?.let {
                (txtPass as EditText).setTextColor(
                    ContextCompat.getColor(
                        context,
                        it
                    )
                )
            }
            listTheme?.fontText?.let {
                (tvSetPinCode as TextView).typeface = ResourcesCompat.getFont(context, it)
            }
            listTheme?.colorBack?.let {
                (tvBackPin as ImageView).setColorFilter(
                    ContextCompat.getColor(
                        context,
                        it
                    )
                )
            }
            listTheme?.largeImage?.let { imLockPin.setBackgroundResource(it) }
            listTheme?.smallImage?.let { imVSmall.setBackgroundResource(it) }
            (tvSetPinCode as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
            for (i in 0 until sizeNumberPin) {
                dataModel[i].margin = 35
                if (i < 9 || i == 10) {
                    dataModel[i].backgroundPinButton = listTheme?.iconPin
                    dataModel[i].typeFace = listTheme?.fontText
                    dataModel[i].colorNumber =
                        ContextCompat.getColor(context, R.color.white)
                } else {
                    dataModel[i].colorDelete =
                        listTheme?.colorDelete?.let { ContextCompat.getColor(context, it) }
                }
            }
            dataModel[11].backgroundPinButton = R.drawable.round_delete_56
        }

        fun setOriginalScreen(
            sizeNumberPin: Int,
            imLockPin: View,
            txtPass: View,
            tvSetPinCode: View,
            dataModel: ArrayList<DataModel>,
            context: Context
        ) {
            (imLockPin as ImageView).setBackgroundResource(R.drawable.ic_lock)
            (txtPass as EditText).setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            (tvSetPinCode as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            for (i in 0 until sizeNumberPin) {
                dataModel[i].backgroundPinButton = R.drawable.round_pin_set
                dataModel[i].colorNumber =
                    ContextCompat.getColor(context, R.color.black)
                dataModel[i].sizeNumber = 20
                dataModel[i].margin = 80
            }
        }

        fun setThemeUnlockScreen(
            view1Win: View,
            view2Win: View,
            view3Win: View,
            imBackgroundVoice: View,
            imv: View,
            tvSpeak: View,
            tvForget: View,
            contentAddView: View,
            textDescription: View,
            time: View,
            date: View,
            context: Context,
            dataTheme: DataModelTheme?,
            position: Int
        ) {
            dataTheme?.bg?.let { (contentAddView as ConstraintLayout).setBackgroundResource(it) }
            (textDescription as TextView).setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            val background1 = (view1Win as ImageView).background
            val background2 = (view2Win as ImageView).background
            val background3 = (view3Win as ImageView).background
            dataTheme?.bgFunction?.let {
                ContextCompat.getColor(
                    context,
                    it
                )
            }?.let {
                background1.setTint(it)
                background2.setTint(it)
                background3.setTint(it)
            }
            dataTheme?.imVoice?.let { (imBackgroundVoice as ImageView).setImageResource(it) }
            dataTheme?.colorVoice?.let {
                (imv as ImageView).setColorFilter(ContextCompat.getColor(context, it))
            }
            dataTheme?.fontText?.let {
                (tvSpeak as TextView).typeface = ResourcesCompat.getFont(context, it)
                (tvForget as TextView).typeface = ResourcesCompat.getFont(context, it)
            }
            if (position == 7)
                dataTheme?.fontTextForget?.let {
                    (tvForget as TextView).typeface = ResourcesCompat.getFont(context, it)
                }
            dataTheme?.colorText?.let {
                (tvSpeak as TextView).setTextColor(ContextCompat.getColor(context, it))
            }
            dataTheme?.colorText?.let {
                (tvForget as TextView).setTextColor(ContextCompat.getColor(context, it))
            }
            dataTheme?.sizeText1?.let {
                (tvSpeak as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, it.toFloat())
            }
            dataTheme?.sizeText2?.let {
                (tvForget as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, it.toFloat())
            }
            (time as TextView).typeface =
                dataTheme?.fontText?.let { ResourcesCompat.getFont(context, it) }
            (date as TextView).typeface =
                dataTheme?.fontText?.let { ResourcesCompat.getFont(context, it) }
            dataTheme?.colorText?.let {
                time.setTextColor(ContextCompat.getColor(context, it))
            }
            dataTheme?.colorText?.let {
                date.setTextColor(ContextCompat.getColor(context, it))
            }
        }

        fun setOriginalThemeUnlockScreen(
            imBackgroundVoice: View,
            contentAddView: View,
            textDescription: View,
            context: Context
        ) {
            (contentAddView as ConstraintLayout).setBackgroundResource(R.drawable.gradient_bg)
            (imBackgroundVoice as ImageView).setImageResource(R.drawable.round_speak)
            (textDescription as TextView).setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )

        }
    }

}