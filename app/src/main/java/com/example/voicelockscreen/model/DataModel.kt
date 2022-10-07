package com.example.voicelockscreen.model

data class DataModel(
    val viewType: Int,
    val number: String,
    var backgroundPinButton: Int? = null,
    var typeFace: Int? = null,
    var colorDelete: Int? = null,
    var colorNumber: Int? = null,
    var sizeNumber: Int? = null,
    var margin: Int? = null,
    var x: Int? = null,
    var y: Int? = null
)