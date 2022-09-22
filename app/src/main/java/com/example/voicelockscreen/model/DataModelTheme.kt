package com.example.voicelockscreen.model

import java.io.Serializable

data class DataModelTheme(
    val colorTheme: Int,
    val colorPinButton: Int? = null,
    val bg: Int? = null,
    val imVoice: Int? = null,
    val colorVoice: Int? = null,
    val bgFunction: Int? = null,
    val fontText: Int? = null,
    val colorText: Int? = null,
    val sizeText1: Int? = null,
    val sizeText2: Int? = null,
    val fontTextForget: Int? = null
) : Serializable