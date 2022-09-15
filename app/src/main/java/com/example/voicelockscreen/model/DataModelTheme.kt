package com.example.voicelockscreen.model

import java.io.Serializable

data class DataModelTheme(
    val colorTheme: Int,
    val colorPinButton: Int? = null,
    val name: String? = null
): Serializable