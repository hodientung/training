package com.example.voicelockscreen.model

data class DataModel(
    val viewType: Int,
    val number: String,
    var backgroundPinButton: Int? = null,
    var typeFace: Int? = null,
    var colorDelete: Int? = null
)