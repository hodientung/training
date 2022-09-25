package com.example.voicelockscreen.model

import java.io.Serializable

data class DataModelTheme(
    val colorTheme: Int? = null,
    val colorPinButton: Int? = null,
    val bg: Int? = null,
    val imVoice: Int? = null,
    val colorVoice: Int? = null,
    val bgFunction: Int? = null,
    val fontText: Int? = null,
    val colorText: Int? = null,
    val sizeText1: Int? = null,
    val sizeText2: Int? = null,
    val fontTextForget: Int? = null,

    val colorBack: Int? = null,
    val largeImage: Int? = null,
    val smallImage: Int? = null,
    val iconPin: Int? = null,
    val iconPattern: Int? = null,
    val colorInputPin: Int? = null,
    val colorPath: Int? = null,
    val colorDelete: Int? = null
) : Serializable